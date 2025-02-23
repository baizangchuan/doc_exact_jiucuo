'''
本代码用于验证得到的结果和标准输出的差异，并打印出有差异的部分
'''
import os
os.system('cls')
import requests
import json


doc_type=[
        "admit_info",# 0
        "discharge_info",# 1 
        "first_case_info",# 2
        "operation_info",# 3
        "case_info", # 4 hhj 
        "inform_info", # 5 hhj done~
        "postoperative_first_case_info"# 6 hhj done~
        ]

doc_type=doc_type[2]

with open(f"./input/{doc_type}_input.json","r", encoding='utf-8') as file:
    data=json.load(file)

url = 'http://111.9.47.74:8921/api/algorithm/process'

response = requests.get(url,json=data)

if response.status_code == 200:
    print("访问成功！")
    # print(response.json)
    print(response)
    json_data = response.json()
    # print("JSON 响应内容：")
    # import pprint
    # pprint.pprint(json_data)
    with open("./get_url.json","w") as f:
        json.dump(json_data,f,indent=4,ensure_ascii=False)
else:
    print("访问失败，状态码：", response.status_code)






root="./"
final_data_dir=root + f"output/{doc_type}/output_example.json"

org_data = json_data
with open(final_data_dir, 'r', encoding='utf-8') as f:
    final_data = json.load(f)


# 以下用于验证rawSeg提取的对不对
org_data_rawSeg=org_data["data"]["result"]["raw_seg"]
final_data_rawSeg=final_data["data"]["result"]["raw_seg"]

# for i in range(len(org_data_rawSeg)):
#     if org_data_rawSeg[i][1]!=final_data_rawSeg[i][1]:
#         print("########")
#         print("---------raw_seg key name: ",org_data_rawSeg[i][0])
#         print("---------目标: \n",org_data_rawSeg[i][1])
#         print("---------我们生成的: \n",final_data_rawSeg[i][1])
#         print("")

# for i,ground_truth_item in enumerate(org_data_rawSeg):
#     print("--all ground truth key name:   ",ground_truth_item[0])
#     # print(ground_truth_item[0])
#     find_flag=0
#     for output_item in final_data_rawSeg:
#         if ground_truth_item[0].replace(" ","") in output_item[0].replace(" ",""):
#             print("success found key name:   ",ground_truth_item[0])
#             find_flag=1
#     if find_flag==0:
#         print("not found key name:   ",ground_truth_item[0])
#     print("")
#     # print

# # print(len(org_data_rawSeg))
# # print(len(final_data_rawSeg))

# for ground_truth_item in final_data_rawSeg:
#     print("all ground truth key name:   ",ground_truth_item[0])




print("*************************************************************************")
print("*************************************************************************")
print("*************************************************************************")
print("*************************************************************************")
print("*************************************************************************")
print("*************************************************************************")

## 以下用于验证normResult组合的对不对
org_data_normResult=org_data["data"]["result"]["norm_result"]
final_data_normResult=final_data["data"]["result"]["norm_result"]

import pprint

#目标中有而我们输出中没有的
for key,value in org_data_normResult.items():
    if final_data_normResult.get(key) is None and len(org_data_normResult[key])!=0:
        print("----------------------------!None Key in output:")
        print(key,":  ")
        pprint.pprint(value)
        print("-----------------")
    elif org_data_normResult[key] is not None and final_data_normResult.get(key):
        if org_data_normResult[key]!=final_data_normResult[key]:
            print(key)
            pprint.pprint(value)
            print("-----------------")
            pprint.pprint(final_data_normResult[key])
    print("###########################################")





print("***************************____**************************")
print("***************************____**************************")
print("***************************____**************************")

#我们输出中有而目标输出中没有的
for key,value in final_data_normResult.items():
    if org_data_normResult.get(key) is None:
        print("----------------------------!我们输出中有而目标输出中没有的: ")
        print(key)
        print("-----------------")
        pprint.pprint(value)
    # elif org_data_normResult[key] is not None and final_data_normResult.get(key):
    #     if org_data_normResult[key]!=final_data_normResult[key]:
    #         print(key)
    #         pprint.pprint(value)
    #         print("-----------------")
    #         pprint.pprint(final_data_normResult[key])
    print("###########################################")


# import pprint
# for key,value in org_data_normResult.items():
#     if org_data_normResult[key]!=final_data_normResult[key]:
#         print(key)
#         pprint.pprint(value)
#         print("###########################################")
#         pprint.pprint(final_data_normResult[key])
#     print("-----------------")