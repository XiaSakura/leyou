import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.LyUploadService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyUploadService.class)
public class FdfsTest {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * 这个是不带缩略图的上传
     */
    @Test
    public void testUpload() throws FileNotFoundException {
        File file = new File("D:\\heima\\upload\\1.png");
        //上传
        StorePath path = storageClient.uploadFile(new FileInputStream(file), file.length(), "png", null);
        // 带分组的路径
        System.out.println(path.getFullPath());
        // 不带分组的路径
        System.out.println(path.getPath());
    }

    /**
     * 这个是带缩略图的上传
     */
    @Test
    public void testUploadAndThumb() throws FileNotFoundException {
        File file = new File("D:\\heima\\upload\\1.png");
        //上传
        StorePath path = storageClient.uploadImageAndCrtThumbImage(new FileInputStream(file), file.length(), "png", null);
        // 带分组的路径
        System.out.println(path.getFullPath());
        // 不带分组的路径
        System.out.println(path.getPath());
        // 获取缩略图路径
        String imagePath = thumbImageConfig.getThumbImagePath(path.getPath());
        System.out.println(imagePath);
    }
}
