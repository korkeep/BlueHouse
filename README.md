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
| begin | 시작일 |
| category | 카테고리 (정치개혁, 일자리 등) |
| content | 내용 |
| crawled_at | 수집시각 |
| end | 종료일 |
| num_agree | 좋아요 |
| replies | 댓글 |
| status | 현재 진행 상황 (청원시작, 청원진행중, 청원종료, 브리핑) |
```python
from petitions_scraper import parse_page

url = 'https://www1.president.go.kr/petitions/<number>'
parse_page(url)
```

```
<Example>
{'begin': '2020-08-01',
 'category': '정치개혁',
 'content': '정부는 국민의 소리를 들으십시오. <중략> 국회도 마찬가지입니다.',
 'crawled_at': '2020-08-01 00:00:00',
 'end': '2020-09-01',
 'num_agree': 10,
 'status': '청원진행중'}
 ```