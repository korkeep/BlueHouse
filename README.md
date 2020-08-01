## What-Citizens-Want
### Project Goal
Keyword Analysis of the Blue-House National Petition Data

### To-Do List
- Acquisition: Raw Data Crawling
- Analysis: Process Data in API format
- Visualization: Android APP Development

### Data Foramt
| Content | Description |
| --- | --- |
| begin | 청원 시작일 |
| category | 청원 카테고리 (외교, 국방, 경제 등) |
| content | 청원 내용 |
| crawled_at | 수집 시각 |
| end | 청원 종료일 |
| num_agree | 수집 시각의 청원 동의 수 |
| replies | 청원 댓글 |
| status | 현재 청원 진행 상황 (청원시작, 청원진행중, 청원종료, 브리핑) |
```python
from petitions_scraper import parse_page

url = 'https://www1.president.go.kr/petitions/<number>'
parse_page(url)
```

```
<Example>
{'begin': '2020-08-01',
 'category': '경제',
 'content': '국민의 소리를 들으십시오.',
 'crawled_at': '2020-08-01 00:00:00',
 'end': '2018-09-01',
 'num_agree': 10,
 'status': '청원진행중'}
 ```