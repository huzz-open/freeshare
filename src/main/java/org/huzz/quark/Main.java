package org.huzz.quark;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.model.Term;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ContentBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ExcerptBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder;

import java.util.List;

/**
 * @author chenji
 * @since 1.0.0
 */
public class Main {

    static String cookie = "";
    static ApiClient client = new ApiClient(cookie);

    static Wordpress wpClient = ClientFactory.fromConfig(ClientConfig.of("http://gaoqian365.com", "", "", false, true));


    public static void main(String[] args)  {
        //Resp<ListFileDescDTO> resp = client.listFileDesc("c17915a87ab8499581ecad2300ea15e5", 1, 2);
        //resp.getData().getList().stream().collect(Collectors.toMap(FileDescDTO::getFid, FileDescDTO::getFileName));

        final Post post = PostBuilder.aPost()
                .withTitle(TitleBuilder.aTitle().withRendered("这是一个API生成的测试文章").build())
                .withExcerpt(ExcerptBuilder.anExcerpt().withRendered("").build())
                .withContent(ContentBuilder.aContent().withRendered("这是文章内容这是文章内容这是文章内容这是文章内容这是文章内容这是文章内容").build())
                .withCategories(List.of(2L))
                .withAuthor(2L)
                .build();

        try {
            final Post createdPost = wpClient.createPost(post, PostStatus.publish);
            System.out.println(createdPost);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }



}
