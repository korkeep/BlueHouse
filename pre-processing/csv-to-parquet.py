"""
convert webtoon schema csv file to parquet using pyarrow
"""
import pyarrow as pa
import pyarrow.parquet as pq
from pyarrow import csv


def convert(filename: str = 'all_comments_hangul'):
    """
    convert csv file to parquet
    :param filename: name of csv without extension
    :return:
    """
    convert_opts = csv.ConvertOptions(column_types={'title_id': pa.uint32(), 'episode_num': pa.uint16(),
                                                    'comment_num': pa.uint16(), 'sentence': pa.string(),
                                                    'registered_time': pa.timestamp('s', 'UTC+09:00')})
    t = csv.read_csv(f'{filename}.csv', convert_options=convert_opts)

    print(t, '\n', len(t))
    pq.write_table(t, f'{filename}')

if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description="Convert csv to parquet")
    parser.add_argument('corpus', type=str, default='../preparation/filtered_best_comments_hangul_corpus.txt',
                        help='filename of corpus')
    args = parser.parse_args()
    convert(args.corpus, args.scores)