#폴더 만들기, 메타데이터 획득
import os
import time
import io
def initialization():
    start_num=584274
    tmp=time.localtime()
    now_year=int(tmp.tm_year)
    now_month=int(tmp.tm_mon)
    for j in range(2020,now_year+1):
        for i in range(1,now_month+1):
            folder_name='raw/%04d/%02d'%(j,i)
            try:
                if not(os.path.isdir('raw/%04d/%02d'%(j,i))):
                    os.makedirs(os.path.join('raw/%04d/%02d'%(j,i)))
            except OSError as e:
                if e.errno != errno.EEXIST:
                    print("Failed to create directory!!!!!")
                    raise
    try:                
        with open('raw/data.txt','r') as metadata:
            for line in metadata:
                start_num = int(line.strip())
                return start_num
                break
    except:
        return start_num
        pass