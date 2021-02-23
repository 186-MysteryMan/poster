package com.angzk;

/**
 * 测试启动类
 *
 * @author Angzk
 * @date 2019年10月23日
 */
public class AngzkRun {

    /**
     * 注: png 格式图片可能会导致 出现黑色背景.
     *
     * @param args main
     */
    public static void main(String[] args) {
        // 获取Resource下 images 的目录
        String folderPath = GraphicsUtils.class.getResource("/images").getPath();

        String spuName = "为你挑选了一个好物";
        String linkUrl = "https://w.url.cn/s/AiNukkx";
        String logoPath = folderPath + "/132.jpg";
        String backgroundUrl = folderPath + "/prototype.png";
        String spuPicUrl = folderPath + "/cat_副本.jpg";
        String memberPrice = "大灰熊";
        String price = "￥199.99";
        String text = "澳洲风味小麦白啤（APA）澳洲风味小麦白啤（APA）澳洲风味小麦白啤（APA）澳洲风味小麦白啤（APA）澳洲风味小麦白啤（APA）澳洲风味小麦白啤（APA）";

        GraphicsUtils.createPosterByRedTemplate(linkUrl, true, logoPath, backgroundUrl, spuPicUrl,
                memberPrice,text, price, spuName);
    }

}
