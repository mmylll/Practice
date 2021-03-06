import requests
import json


class DoubanSpider():
    def __init__(self):
        self.url = {
            "url_temp": ["https://m.douban.com/rexxar/api/v2/subject_collection/tv_american/items?start={}&count=18&loc_id=108288",
                        "https://m.douban.com/rexxar/api/v2/subject_collection/tv_domestic/items?&start={}&count=18&loc_id=108288",
                        "https://m.douban.com/rexxar/api/v2/subject_collection/tv_korean/items?start={}&count=18&loc_id=108288",
                        "https://m.douban.com/rexxar/api/v2/subject_collection/tv_animation/items?start={}&count=18&loc_id=108288",
                        "https://m.douban.com/rexxar/api/v2/subject_collection/tv_variety_show/items?start={}&count=18&loc_id=108288",
                        "https://m.douban.com/rexxar/api/v2/subject_collection/tv_japanese/items?start={}&count=18&loc_id=108288"],
            "country": "us"}
        self.headers = {"Referer": ["https://m.douban.com/tv/american",
                                    "https://m.douban.com/tv/chinese",
                                    "https://m.douban.com/tv/korean",
                                    "https://m.douban.com/tv/animation",
                                    "https://m.douban.com/tv/tvshow",
                                    "https://m.douban.com/tv/japanese"],
                        "User-Agent": ["Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Mobile Safari/537.36",
                                      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36",
                                      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36",
                                      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36",
                                      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36",
                                      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"]
                        }

    def parse_url(self, url):  # ???????????????????????????
        print(url)
        response = requests.get(url, headers=self.headers)
        return response.content.decode()

    def get_content_list(self, json_str):  # ????????????
        dict_ret = json.loads(json_str)  # ?????????python????????????
        content_list = dict_ret["subject_collection_items"]  # ?????????????????????????????????????????????????????????????????????
        return content_list

    def save_content_list(self, content_list, country):  # ????????????
        for content in content_list:
            with open('douban_my.txt', 'a', encoding='UTF-8') as f:
                content["country"] = country
                f.write(json.dumps(content, ensure_ascii=False))
                f.write('\n')
        print('????????????')

    def run(self):  # ??????????????????
        num = 0
        while True:
            # 1??????????????????url
            start_url = self.url["url_temp"].format(num)
            # 2??????????????????????????????
            json_str = self.parse_url(start_url)
            # 3???????????????
            content_list = self.get_content_list(json_str)
            # # 4?????????
            self.save_content_list(content_list, self.url["country"])
            # 5??????????????????url
            if len(content_list) < 18:  # ??????????????????????????????
                break
            num += 18


if __name__ == '__main__':
    doubanspider = DoubanSpider()
    doubanspider.run()