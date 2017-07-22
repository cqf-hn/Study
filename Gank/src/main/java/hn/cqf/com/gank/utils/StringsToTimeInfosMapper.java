package hn.cqf.com.gank.utils;

import java.util.ArrayList;
import java.util.List;

import hn.cqf.com.pickerview.bean.TimeInfo;
import io.reactivex.functions.Function;

/**
 * @author cqf
 * @time 2017/1/16 0016  下午 6:08
 * @desc ${TODD}
 */
public class StringsToTimeInfosMapper implements Function<List<String>, List<TimeInfo>> {
    private static StringsToTimeInfosMapper INSTANCE = new StringsToTimeInfosMapper();

    private StringsToTimeInfosMapper() {
    }

    public static StringsToTimeInfosMapper getInstance() {
        return INSTANCE;
    }

    /**
     *
     * @param strings
     * @return
     * @throws Exception
     */
    @Override
    public List<TimeInfo> apply(List<String> strings) throws Exception {
        List<TimeInfo> infos = new ArrayList<>();
        TimeInfo info;
        for (int i = 0; i < strings.size(); i++) {
            info = new TimeInfo();
            String time = strings.get(i);
            String[] split = time.split("-");
            for (int j = 0; j < split.length; j++) {
                int parseInt = Integer.parseInt(split[j]);
                switch (j) {
                    case 0:
                        info.setYear(parseInt);
                        break;
                    case 1:
                        info.setMonth(parseInt);
                        break;
                    case 2:
                        info.setDay(parseInt);
                        break;
                }
            }
            infos.add(info);
        }
        return infos;
    }
}
