package lzss.paramsreader;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum RunParams {
    COMPRESS("-c", new CompressParamService()),
    COMPRESS_ALL("-ca", new CompressAllParamsService()),
    UNCOMPRESS("-dc", new UncompressParamService()),
    UNKNOWN("", new DoNthParamService());

    private String value;
    private ParamService paramService;

    public static RunParams getByValue(String...values) {
        if (values.length == 0)
            return UNKNOWN;

        String firstArgument = values[0];
        return Stream.of(RunParams.values())
                     .filter(en -> en.getValue().equals(firstArgument))
                     .findFirst()
                     .orElse(UNKNOWN);
    }
}
