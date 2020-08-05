from petitions_scraper import parse_page
import data_crawling_init
import json
import os
import io
import time

start_num = data_crawling_init.initialization()
'''try:
    while(1):
        url='https://www1.president.go.kr/petitions/{}'.format(start_num)
        raw_data=parser.parse_page(url)
        data_year=(raw_data['begin'])[0:4]
        data_month=(raw_data['begin'])[5:7]
        #파일 쓰기
        with open('raw/{}/{}/data_{}.json'.format(data_year,data_month,start_num),'w',encoding='utf-8') as outfile:
            json.dump(raw_data,outfile,ensure_ascii=False,indent=4)
        start_num = start_num + 1
except:
    print(start_num-1,'까지 정보를 받아옴')
    with open('raw/data.txt','w') as metadata:
        metadata.write(str(start_num))'''

flag=0
real_flag=0
stop_point=0

while(1):
    time.sleep(0.2)
    url='https://www1.president.go.kr/petitions/{}'.format(str(start_num))
    raw_data=parse_page(url)
    if(raw_data != -1):
        if flag==1:
            start_num=stop_point
        stop_point=0
        flag=0
        real_flag=0
        print(start_num)
        data_year=(raw_data['begin'])[0:4]
        data_month=(raw_data['begin'])[5:7]
        #파일 쓰기
        with open('raw/{}/{}/data_{}.json'.format(data_year,data_month,start_num),'w',encoding='utf-8') as outfile:
            json.dump(raw_data,outfile,ensure_ascii=False,indent=4)
        start_num = start_num + 1
    else:
        if stop_point == 0:
            stop_point=start_num
        print(flag, start_num)
        flag = flag + 1
        start_num = start_num+1
        if flag==10:
            print('에러 발생. 5초 대기.')
            time.sleep(5)
            real_flag = real_flag + 1
            flag=0
            start_num=stop_point
        if real_flag==2:
            print('더이상 불러올 수 없음')
            break

start_num=stop_point
print(start_num-1,'까지 정보를 받아옴')
with open('raw/data.txt','w') as metadata:
    metadata.write(str(start_num))