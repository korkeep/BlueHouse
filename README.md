## 사전준비

미리 설치해야 하는 항목은 아래와 같다.

```python
pip install beautifulsoup4
pip install lxml
```

## 실행

data_crawling.py 를 실행한다.


## 작동방식

1) data_crawling.py가 data_crawling_init.py를 import해 데이터를 저장할 폴더, 메타 데이터를 생성

2) url 설정

3) petitions scraper의 parse.py의 parse_page를 호출해 정제된 데이터 추출(만약 추출 불가능 하면 -1 리턴)

3-1) -1을 연속 1-0번 받으면 그 지점을 5초후 다시 시행(한번의 기회)

4) 더이상 추출할 수 없으면 종료