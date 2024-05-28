package org.huzz.freeshare.pdf;

/**
 * @author chenji
 * @since 1.0.0
 */

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcessPDFFiles {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);
    static AtomicInteger c = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        String directoryPath = "C:\\data\\documents\\电子书\\标准图书\\";  // 替换为您的目录路径

        File directory = new File(directoryPath);
        processFilesInDirectory(directory);

        executorService.shutdown();
    }

    private static void processFilesInDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    processFilesInDirectory(file);  // 递归处理子目录
                } else {
                    executorService.submit(() -> {
                        c.incrementAndGet();
                        processFile(file);
                    });
                }
            }
        }
    }

    private static void processFile(File file) {
        try {
            // 加载PDF文档
            PDDocument document = Loader.loadPDF(file);

            // 创建PDF渲染器
            PDFRenderer renderer = new PDFRenderer(document);

            // 渲染PDF页面为图像
            BufferedImage image = renderer.renderImageWithDPI(0, 96); // 第二个参数是图像的分辨率，这里设置为300 dpi

            // 保存图像
            String outputFileName = file.getName() + ".png";
            File outputImage = new File("C:\\data\\documents\\电子书\\封面", outputFileName);
            ImageIO.write(image, "PNG", outputImage);

            // 关闭文档
            document.close();
        } catch (Exception e) {
            System.out.printf("处理文件失败: %s\n", file.getName());
        }
    }
}