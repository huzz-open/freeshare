package org.huzz.freeshare.wp;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.huzz.freeshare.quark.dto.FileDescDTO;
import org.huzz.freeshare.quark.dto.ListFileDescDTO;
import org.huzz.freeshare.quark.dto.Resp;
import org.huzz.freeshare.quark.rest.QuarkClient;
import org.huzz.freeshare.wp.constant.PostsAction;
import org.huzz.freeshare.wp.dto.CreatePostsResp;
import org.huzz.freeshare.wp.dto.Posts;
import org.huzz.freeshare.wp.rest.WpClient;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenji
 * @since 1.0.0
 */
public class Main {
    static final String cookie = "";
    static WpClient client = new WpClient(cookie);
    static QuarkClient quarkClient = new QuarkClient("");
    static ExecutorService executors = Executors.newFixedThreadPool(3);

    static AtomicLong postsId = new AtomicLong(50);
    public static void main(String[] args) throws Exception {
        Resp<ListFileDescDTO> fileDesc = quarkClient.listFileDesc("c17915a87ab8499581ecad2300ea15e5", 1, 500);

        String files = IOUtils.toString(Objects.requireNonNull(Main.class.getResource("/png.txt")), StandardCharsets.UTF_8);
        String[] lines = files.split(StringUtils.LF);
        for (String line : lines) {
            // line:  20240526191632636-图灵程序设计丛书.AngularJS权威教程.ng-book2.pdf.png
            String pdfImageName = line.substring(18);
            String pdfName = pdfImageName.substring(0, pdfImageName.length() - 5);
            executors.submit(() -> {
                    try {
                        Posts build = Posts.builder()
                                .id(0L)
                                .action(PostsAction.posts_save)
                                .title(pdfName)
                                .content(content(link, password, pdfName, "https://gaoqian365.com/wp-content/uploads/2024/05/" + line)) // https://gaoqian365.com/wp-content/uploads/2024/05/20240526192605300-图灵程序设计丛书.AngularJS权威教程.ng-book.pdf.png
                                .category(List.of(39, 41))
                                .build();
                        CreatePostsResp posts = client.createPosts(build);
                        System.out.println("创建成功：" + posts);
                    } catch (Exception e) {
                        System.out.println("创建失败：" + pdfName);
                    }
                }
            );

        }
        executors.shutdown();
    }

    public static String content(String link, String password, String pdfName, String imagePath) {
        return String.format("<div class=\"quote_q quote-mce qe_wzk_lan\" contenteditable=\"false\"><div contenteditable=\"true\"><p>下载链接：</p><div class=\"tinymce-hide\" contenteditable=\"false\"><p class=\"hide-before\">[hidecontent type=\"payshow\" desc=\"隐藏内容：付费阅读\"]</p><div contenteditable=\"true\"><p>链接：%s</p><p>提取密码：%s</p></div><p class=\"hide-after\">[/hidecontent]</p></div></div></div><p><img src=\"%s\" alt=\"%s\" data-full-url=\"%s\" /></p>", link, password, imagePath, pdfName, imagePath);
    }
}
