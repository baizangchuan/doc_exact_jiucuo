'''
本代码用于验证得到的结果和标准输出的差异，并打印出有差异的部分
'''



import json


org_json_dir="output_example.json"
final_data_dir="final_data_py.json"

with open(org_json_dir, 'r', encoding='utf-8') as f:
    org_data = json.load(f)

with open(final_data_dir, 'r', encoding='utf-8') as f:
    final_data = json.load(f)




# ## 以下用于验证normResult组合的对不对
# org_data_normResult=org_data["data"]["result"]["norm_result"]
# final_data_normResult=final_data["data"]["result"]["norm_result"]

# import pprint
# for key,value in org_data_normResult.items():
#     if org_data_normResult[key]!=final_data_normResult[key]:
#         print(key)
#         pprint.pprint(value)
#         print("###########################################")
#         pprint.pprint(final_data_normResult[key])
#     print("-----------------")



# 以下用于验证rawSeg提取的对不对
org_data_rawSeg=org_data["data"]["result"]["raw_seg"]
final_data_rawSeg=final_data["data"]["result"]["raw_seg"]

for i in range(len(org_data_rawSeg)):
    if org_data_rawSeg[i][1]!=final_data_rawSeg[i][1]:
        print("---------")
        print(org_data_rawSeg[i][0])
        print("########")
        print(org_data_rawSeg[i][1])
        print(final_data_rawSeg[i][1])
        print("---------")


