import json
import re

def get_content_from_input_json_dir(dir):#提取input中的content
    with open(dir, 'r', encoding='utf-8') as f:
        data = json.load(f)
        content = data['args']['content']
        return content
    
def get_rawSeg_key_set_from_format_guide_json(format_guide_json_dir):#从目标输出中提取rawSeg_key_set，即所有待匹配的关键词
    with open(format_guide_json_dir, 'r', encoding='utf-8') as f:
        data = json.load(f)
        rawSeg = data['data']['result']['raw_seg']
    rawSeg_key_set=[]
    for rawSeg_item in rawSeg:
        rawSeg_key_set.append(rawSeg_item[0])
    return rawSeg_key_set

    
def extract_content(input_string, start_marker, end_marker):#在string中，提取a和b之间的内容并返回
    pattern = re.compile(f'{start_marker}(.*?){end_marker}', re.DOTALL)
    match = pattern.search(input_string)
    if match:
        return match.group(1)
    else:
        return None

def remove_text_before(text_a, text_b):#从文本a中删除文本b及其之前的内容
    index = text_a.find(text_b)
    if index != -1:
        return text_a[index + len(text_b):]
    else:
        return text_a    



def get_rawSeg(content,rawSeg_key_set, start_word="", end_word=""):#由原文和关键字集合，得到每个关键字对应的字段
    rawSeg=[]
    input_string = content
    for i in range(0,len(rawSeg_key_set)-1):
        if i==0:
            start_marker = ''
            end_marker = rawSeg_key_set[i+1]
        else:
            start_marker = rawSeg_key_set[i]
            end_marker = rawSeg_key_set[i+1]
        result = extract_content(input_string, start_marker, end_marker).strip().replace("\t", "")
        seg_pair=[start_marker,result]
        rawSeg.append(seg_pair)
        input_string=remove_text_before(input_string,result) #去除掉已经匹配到的字段，匹配一段就删去一段 
    #最末
    final_key=rawSeg_key_set[-1]
    index = input_string.find(final_key)
    result=input_string[index + len(final_key):].strip().replace("\t", "")
    seg_pair=[final_key,result]
    rawSeg.append(seg_pair) 

    return rawSeg


def get_normResult_guide_from_format_guide_json(format_guide_json):#从目标输出文件中，找出normResult格式参考列表
    with open(format_guide_json, 'r', encoding='utf-8') as f:
        data = json.load(f)
        normResult = data['data']['result']['norm_result']
    normResult_guide=[]
    for item_name, normResult_item_list in normResult.items():
        result_item=[item_name,[]]
        for normResult_item_list_dict in normResult_item_list:
            result_item[1].append(normResult_item_list_dict["key"])
        normResult_guide.append(result_item)
    return normResult_guide



'''
二级提取，对rawSeg中的某一项文本再做二级拆分，返回一个列表
对一段文本content= "xxx_A_aaa_B_bbb_C_ccc_D_ddd_Endword_xxxxx"
给一串key_list=["A","B","C","D"]和 参数Endword = "Endword"
返回一个由dict组成的列表：
extract_list=[
                {
                    "key": "A",
                    "value": "aaa"
                },
                ...
                {
                    "key": "D",
                    "value": "ddd"
                },                
            ]
'''
def sub_extract(rawSeg, rawSeg_item_name, key_list=["T","P","R","BP"], Endword=""):
    for item in rawSeg:#找到待匹配的源文本
        if item[0]==rawSeg_item_name:
            content=item[1]

    key_list.append(Endword)
    extract_list=[]
    input_string = content
    for i in range(len(key_list)-1):
        start_marker = key_list[i]
        end_marker = key_list[i+1]
        value = extract_content(input_string, start_marker, end_marker).strip().strip("：").strip(":").replace("\t", "")
        extract_item={
            "key":key_list[i],
            "value":value
        }
        extract_list.append(extract_item)
    return extract_list


def make_normResult_item( 
                         normResult, #待修改的字典
                         item_name, #待加入的字段名
                         rawSeg, #原始数据，要在里面提取字段
                         rawSeg_item_key  #需要加入的中文字段集，会在rawSeg中查找
                         ):
    normResult_item_list=[]
    #单独处理operation_process的情况，把两个“手术经过”的
    if item_name=="operation_process":
        for rawSeg_item in rawSeg:
            if rawSeg_item[0]=="手术经过：":
                normResult_item_list_item={
                    "value":rawSeg_item[1],
                    "key":rawSeg_item[0]
                }
                normResult_item_list.append(normResult_item_list_item)
        temp={
                    "value":normResult_item_list[0]["value"]+normResult_item_list[1]["value"],
                    "key":normResult_item_list[0]["key"]
                }
        normResult_item_list=[]
        normResult_item_list.append(temp)
        normResult.update({item_name:normResult_item_list})
        return

    for key in rawSeg_item_key: #将中文字段的key-value对组成一个列表
        notFound=1
        for rawSeg_item in rawSeg:
            if key==rawSeg_item[0]:
                notFound=0
                normResult_item_list_item={
                    "value":rawSeg_item[1],
                    "key":key
                }
                if normResult_item_list_item not in normResult_item_list :#同一个key在rawSeg中出现多次的情况
                    normResult_item_list.append(normResult_item_list_item)
                    break #防止把所有“查体都找出来，而是仅找出当前的”
        #没找到的情况
        if notFound:
            normResult_item_list.append({
                "value":"",
                "key":key
            })
    normResult.update({item_name:normResult_item_list})


def get_normResult_from_normResult_guide(normResult_guide,rawSeg):#由normResult格式参考列表得到normResult
    normResult=dict()
    for normResult_guide_item in normResult_guide:
        make_normResult_item(normResult,
                        normResult_guide_item[0],
                        rawSeg,
                        normResult_guide_item[1]
                        )
    return normResult

def gen_final_data(normResult,rawSeg):
    final_data=dict()
    final_data.update(
{
    "code": 0,
    "data": {
      "sample_rate": None,
      "sample_count": None,
      "result": {
        "record_type": "postoperative_first_case_info",
        "record_title": "术后首次病程记录",
        "record_date": None,
        "norm_result": normResult,
        "norm_res_sub_seg": {},
        "raw_seg": rawSeg,
        "msg": "ok",
        "code": 0
      }
    }
  }
        )
    return final_data

def main():
    
    input_json_dir="/data1/hhj/project/demo/template/Death_Info/input.json"
    format_guide_json="/data1/hhj/project/demo/template/Death_Info/output_example.json"#格式参考文件的路径


    #由输入得到content
    content=get_content_from_input_json_dir(dir=input_json_dir)
        # print(content)

    #由format_guide_json得到rawSeg_key_set
    rawSeg_key_set=get_rawSeg_key_set_from_format_guide_json(format_guide_json)
        # print(rawSeg_key_set)

    #由content得到rawSeg
    rawSeg=get_rawSeg(content,rawSeg_key_set)
    # print(rawSeg)

    #由格式参考文件得到目标输出的格式列表normResult_guide
    normResult_guide=get_normResult_guide_from_format_guide_json(format_guide_json=format_guide_json)
    # print(normResult_guide)

    #由格式列表normResult_guide得到模板数据段normResult
    normResult=get_normResult_from_normResult_guide(normResult_guide,rawSeg)

    # key_metrics=sub_extract(rawSeg=rawSeg, 
    #                     rawSeg_item_name="体格检查", 
    #                     key_list=["T","P","R","BP"],
    #                     Endword="生命征")
    # normResult.update({"key_metrics":key_metrics})

    # import pprint
    # pprint.pprint(normResult)

    #将normResult嵌入目标输出json文档中
    final_data=gen_final_data(normResult,rawSeg)

    #保存
    with open("/data1/hhj/project/demo/template/Death_Info/final_data_py.json", 'w') as json_file:
        json.dump(final_data, json_file, indent=4, ensure_ascii=False)

if __name__ == "__main__":
    main()




