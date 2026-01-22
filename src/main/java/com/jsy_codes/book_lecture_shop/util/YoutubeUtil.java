package com.jsy_codes.book_lecture_shop.security;

public class YoutubeUtil {
    public static String extractId(String url) {
        if (url.contains("v=")) {
            return url.substring(url.indexOf("v=") + 2).split("&")[0];
        }
        if (url.contains("youtu.be/")) {
            return url.substring(url.indexOf("youtu.be/") + 9);
        }
        return url; // 실패하면 원문 반환
    }
}
/*templates/
 └─ course/
      author-course-list.html
      author-course-form.html
      author-course-edit.html
      course-detail.html
      course-player.html
*/
