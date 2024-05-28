package org.huzz.freeshare.quark;

import org.huzz.freeshare.quark.dto.FileDescDTO;
import org.huzz.freeshare.quark.dto.ListFileDescDTO;
import org.huzz.freeshare.quark.dto.Resp;
import org.huzz.freeshare.quark.rest.QuarkClient;

import java.util.stream.Collectors;

/**
 * @author chenji
 * @since 1.0.0
 */
public class Main {

    static String cookie = "";
    static QuarkClient client = new QuarkClient(cookie);

    public static void main(String[] args)  {
        Resp<ListFileDescDTO> resp = client.listFileDesc("c17915a87ab8499581ecad2300ea15e5", 1, 2);
        resp.getData().getList().stream().collect(Collectors.toMap(FileDescDTO::getFid, FileDescDTO::getFileName));
    }

}
