/**
 *
 */
package cn.foxnickel.listentome.utils.voice.result.util;


import java.util.ArrayList;

import cn.foxnickel.listentome.utils.voice.result.entity.Phone;
import cn.foxnickel.listentome.utils.voice.result.entity.Sentence;
import cn.foxnickel.listentome.utils.voice.result.entity.Syll;
import cn.foxnickel.listentome.utils.voice.result.entity.Word;

/**
 * <p>Title: ResultFormatUtl</p>
 * <p>Description: </p>
 * <p>Company: www.iflytek.com</p>
 *
 * @author iflytek
 * @date 2015年1月19日 上午10:01:14
 */
public class ResultFormatUtil {

    /**
     * 将英语评测详情按格式输出
     *
     * @param sentences
     * @return 英语评测详情
     */
    public static String formatDetails_EN(ArrayList<Sentence> sentences) {
        StringBuffer buffer = new StringBuffer();
        if (null == sentences) {
            return buffer.toString();
        }

        for (Sentence sentence : sentences) {
            if ("噪音".equals(ResultTranslateUtil.getContent(sentence.content))
                    || "静音".equals(ResultTranslateUtil.getContent(sentence.content))) {
                continue;
            }

            if (null == sentence.words) {
                continue;
            }
            for (Word word : sentence.words) {
                if ("噪音".equals(ResultTranslateUtil.getContent(word.content))
                        || "静音".equals(ResultTranslateUtil.getContent(word.content))) {
                    continue;
                }

                buffer.append("\n单词[" + ResultTranslateUtil.getContent(word.content) + "] ")
                        .append(" 得分：" + String.valueOf((word.total_score / 5) * 100));
                if (null == word.sylls) {
                    buffer.append("\n");
                    continue;
                }

                buffer.append("\n");
            }
        }

        return buffer.toString();
    }

    /**
     * 将汉语评测详情按格式输出
     *
     * @param sentences
     * @return 汉语评测详情
     */
    public static String formatDetails_CN(ArrayList<Sentence> sentences) {
        StringBuffer buffer = new StringBuffer();
        if (null == sentences) {
            return buffer.toString();
        }

        for (Sentence sentence : sentences) {
            if (null == sentence.words) {
                continue;
            }

            for (Word word : sentence.words) {
                buffer.append("\n词语[" + ResultTranslateUtil.getContent(word.content) + "] " + word.symbol + " 时长：" + word.time_len);
                if (null == word.sylls) {
                    continue;
                }

                for (Syll syll : word.sylls) {
                    if ("噪音".equals(ResultTranslateUtil.getContent(syll.content))
                            || "静音".equals(ResultTranslateUtil.getContent(syll.content))) {
                        continue;
                    }

                    buffer.append("\n└音节[" + ResultTranslateUtil.getContent(syll.content) + "] " + syll.symbol + " 时长：" + syll.time_len);
                    if (null == syll.phones) {
                        continue;
                    }

                    for (Phone phone : syll.phones) {
                        buffer.append("\n\t└音素[" + ResultTranslateUtil.getContent(phone.content) + "] " + "时长：" + phone.time_len)
                                .append(" 朗读：" + ResultTranslateUtil.getDpMessageInfo(phone.dp_message));
                    }

                }
                buffer.append("\n");
            }
        }

        return buffer.toString();
    }
}