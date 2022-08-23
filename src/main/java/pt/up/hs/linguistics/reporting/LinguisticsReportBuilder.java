package pt.up.hs.linguistics.reporting;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import pt.up.hs.linguini.models.SentenceSummary;
import pt.up.hs.linguistics.domain.Emotion;
import pt.up.hs.linguistics.client.sampling.dto.Text;
import pt.up.hs.linguistics.domain.Sentence;
import pt.up.hs.linguistics.domain.enumeration.PoSTag;
import pt.up.hs.linguistics.reporting.sheet.ExcelSheet;
import pt.up.hs.linguistics.reporting.workbook.ExcelWorkbook;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LinguisticsReportBuilder {

    private final MessageSource i18n;

    //private final ExcelSheet summarySheet = new ExcelSheet();
    private final Map<String, Object[]> summaryValues = new HashMap<>();
    private ExcelSheet emotionalSummarySheet = null;
    private final Map<String, ExcelSheet> wordFrequenciesSheets = new HashMap<>();
    private final Map<String, ExcelSheet> posTagsSheets = new HashMap<>();
    private final Map<String, ExcelSheet> coOccurrencesSheets = new HashMap<>();
    private final Map<String, ExcelSheet> sentenceSheets = new HashMap<>();
    private final Map<String, ExcelSheet> emotionalSheets = new HashMap<>();

    public LinguisticsReportBuilder(
        MessageSource i18n
    ) {
        this.i18n = i18n;
        createSummarySheet();
    }

    public LinguisticsReportBuilder newSummaryLine(
        String code,
        String task, String participant,
        Integer characterCount, Integer nonBlankCharacterCount,
        Integer wordCount, Integer distinctWordCount,
        Integer functionWordCount, Integer distinctFunctionWordCount,
        Integer contentWordCount, Integer distinctContentWordCount,
        Integer distinctLemmaCount,
        Double wordAvgLength,
        Double functionWordAvgLength,
        Double contentWordAvgLength,
        Integer sentenceCount,
        Double sentenceAvgWords,
        Double lexicalDensity,
        Double baseTTR, Double hdd, Double mtld, Double vocd,
        Double ideaDensity
    ) {
        summaryValues.put(code, new Object[] {
            code, task, participant,
            characterCount, nonBlankCharacterCount,
            wordCount, distinctWordCount,
            functionWordCount, distinctFunctionWordCount,
            contentWordCount, distinctContentWordCount,
            distinctLemmaCount,
            wordAvgLength,
            functionWordAvgLength,
            contentWordAvgLength,
            sentenceCount,
            sentenceAvgWords,
            lexicalDensity,
            baseTTR, hdd, mtld, vocd,
            ideaDensity
        });
        return this;
    }

    public LinguisticsReportBuilder newWordFrequencySheet(
        String code,
        Map<String, Integer> wfContent,
        Map<String, Integer> wfFunction
    ) {
        ExcelSheet sheet = new ExcelSheet();
        sheet.setName(
            i18n.getMessage("report.wordfrequency", new String[]{code}, LocaleContextHolder.getLocale())
        );
        sheet.setHeader(
            i18n.getMessage("report.wordfrequency.word", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.wordfrequency.type", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.wordfrequency.frequency", null, LocaleContextHolder.getLocale())
        );

        List<WordFrequencyType> values = wfContent.entrySet()
            .parallelStream()
            .map(e -> new WordFrequencyType(e.getKey(), e.getValue(), "C"))
            .collect(Collectors.toList());
        values.addAll(wfFunction.entrySet()
            .parallelStream()
            .map(e -> new WordFrequencyType(e.getKey(), e.getValue(), "F"))
            .collect(Collectors.toList()));

        values.sort(Comparator.comparingInt(v -> ((WordFrequencyType) v).freq).reversed());
        values.forEach(v -> sheet.addRow(new Object[]{v.word, v.type, v.freq}));

        wordFrequenciesSheets.put(code, sheet);

        return this;
    }

    public LinguisticsReportBuilder newPoSTagSheet(
        String code, Map<PoSTag, Set<String>> grammaticalCategories
    ) {
        ExcelSheet sheet = new ExcelSheet();
        sheet.setName(
            i18n.getMessage("report.postag", new String[]{code}, LocaleContextHolder.getLocale())
        );
        sheet.setHeader(
            i18n.getMessage("report.postag.type", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.postag.count", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.postag.words", null, LocaleContextHolder.getLocale())
        );

        grammaticalCategories.entrySet().parallelStream()
            .sorted(Comparator.comparingInt(e -> ((Map.Entry<PoSTag, Set<String>>) e).getValue().size()).reversed())
            .forEachOrdered(e -> {
                sheet.addRow(new Object[]{
                    e.getKey().toString(),
                    e.getValue().size(),
                    String.join("; ", e.getValue())
                });
            });

        posTagsSheets.put(code, sheet);

        return this;
    }

    public LinguisticsReportBuilder newCoOccurrencesSheet(
        String code, Map<String, Double> coOccurrences
    ) {
        ExcelSheet sheet = new ExcelSheet();
        sheet.setName(
            i18n.getMessage("report.cooccurrence", new String[]{code}, LocaleContextHolder.getLocale())
        );
        sheet.setHeader(
            i18n.getMessage("report.cooccurrence.word", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.cooccurrence.word", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.cooccurrence.value", null, LocaleContextHolder.getLocale())
        );

        coOccurrences.entrySet()
            .parallelStream()
            .sorted(Comparator.comparingDouble(e -> ((Map.Entry<String, Double>) e).getValue()).reversed())
            .forEachOrdered(c -> {
                String[] words = c.getKey().split("--");
                if (words.length < 2) {
                    return;
                }
                sheet.addRow(new Object[]{words[0], words[1], c.getValue()});
            });

        coOccurrencesSheets.put(code, sheet);

        return this;
    }

    public LinguisticsReportBuilder newEmotionalSheet(
        String code,
        Set<Emotion> emotions,
        Text text
    ) {

        ExcelSheet sheet = new ExcelSheet();
        sheet.setName(
            i18n.getMessage("report.emotions", new String[]{code}, LocaleContextHolder.getLocale())
        );
        sheet.setHeader(
            i18n.getMessage("report.emotions.category", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.emotions.count", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.emotions.percentage", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.emotions.words", null, LocaleContextHolder.getLocale())
        );

        String textStr = text.getText();
        Map<String, List<String>> wordsByEmotion = emotions.stream()
            .collect(Collectors.groupingBy(
                Emotion::toDisplayString,
                Collectors.mapping(
                    e -> textStr.substring(e.getStart(), e.getStart() + e.getSize()).toLowerCase(),
                    Collectors.toList()
                )
            ));

        Map<String, Long> groupedEmotions = emotions.stream()
            .collect(Collectors.groupingBy(Emotion::toDisplayString, Collectors.counting()));
        groupedEmotions.entrySet().stream()
            .sorted(Comparator.comparingLong(Map.Entry<String, Long>::getValue).reversed())
            .forEachOrdered(emotion -> {
                Set<String> words = wordsByEmotion.getOrDefault(emotion.getKey(), new ArrayList<>())
                    .stream().parallel()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .map(e -> e.getValue() > 1 ? String.format("%s (%d)", e.getKey(), e.getValue()) : e.getKey())
                    .collect(Collectors.toSet());

                sheet.addRow(new Object[]{
                    emotion.getKey(),
                    emotion.getValue(),
                    (double) emotion.getValue() / emotions.size() * 100,
                    String.join("; ", words)
                });
            });

        emotionalSheets.put(code, sheet);

        return this;
    }

    public LinguisticsReportBuilder newSentenceSummarySheet(
        String code,
        Set<Sentence> sentences,
        Text text
    ) {

        ExcelSheet sheet = new ExcelSheet();
        sheet.setName(
            i18n.getMessage("report.sentences", new String[]{code}, LocaleContextHolder.getLocale())
        );
        sheet.setHeader(
            i18n.getMessage("report.sentences.sentence", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.sentences.words", null, LocaleContextHolder.getLocale())
        );

        String textStr = text.getText();
        for (Sentence sentence: sentences) {
            String sentenceStr = textStr.substring(
                sentence.getStart(),
                Math.min(sentence.getStart() + sentence.getSize(), textStr.length())
            );
            sheet.addRow(new Object[]{
                sentenceStr,
                sentence.getNrOfWords()
            });
        }

        sentenceSheets.put(code, sheet);

        return this;
    }

    public LinguisticsReportBuilder createEmotionalSummarySheet(
        List<Set<Emotion>> emotions,
        List<Text> texts
    ) {

        ExcelSheet sheet = new ExcelSheet();
        sheet.setName(
            i18n.getMessage("report.emotionsSummary", null, LocaleContextHolder.getLocale())
        );
        sheet.setHeader(
            i18n.getMessage("report.emotions.category", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.emotions.count", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.emotions.percentage", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.emotions.words", null, LocaleContextHolder.getLocale())
        );

        Map<String, List<String>> wordsByEmotion = new HashMap<>();
        for (int i = 0; i < emotions.size(); i++) {
            Set<Emotion> textEmotions = emotions.get(i);
            String textStr = texts.get(i).getText();
            Map<String, List<String>> textWordsByEmotion = textEmotions.stream()
                .collect(Collectors.groupingBy(
                    Emotion::toDisplayString,
                    Collectors.mapping(
                        e -> textStr.substring(e.getStart(), e.getStart() + e.getSize()).toLowerCase(),
                        Collectors.toList()
                    )
                ));
            textWordsByEmotion.keySet().forEach(key -> {
                if (wordsByEmotion.containsKey(key)) {
                    wordsByEmotion.get(key).addAll(textWordsByEmotion.get(key));
                } else {
                    wordsByEmotion.put(key, textWordsByEmotion.get(key));
                }
            });
        }

        Set<Emotion> allEmotions = emotions.stream()
            .flatMap(Set::stream).collect(Collectors.toSet());
        Map<String, Long> groupedEmotions = allEmotions.stream()
            .collect(Collectors.groupingBy(Emotion::toDisplayString, Collectors.counting()));
        groupedEmotions.entrySet().stream()
            .sorted(Comparator.comparingLong(Map.Entry<String, Long>::getValue).reversed())
            .forEachOrdered(emotion -> {
                Set<String> words = wordsByEmotion.getOrDefault(emotion.getKey(), new ArrayList<>())
                    .stream().parallel()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .map(e -> e.getValue() > 1 ? String.format("%s (%d)", e.getKey(), e.getValue()) : e.getKey())
                    .collect(Collectors.toSet());

                sheet.addRow(new Object[]{
                    emotion.getKey(),
                    emotion.getValue(),
                    (double) emotion.getValue() / allEmotions.size() * 100,
                    String.join("; ", words)
                });
            });

        this.emotionalSummarySheet = sheet;

        return this;
    }

    public ExcelWorkbook conclude() {

        ExcelWorkbook workbook = new ExcelWorkbook();

        List<String> codes = summaryValues.keySet().parallelStream()
            .sorted(Comparator.naturalOrder()).collect(Collectors.toList());

        ExcelSheet summarySheet = createSummarySheet();
        for (String code: codes) {
            summarySheet.addRow(summaryValues.get(code));
        }
        workbook.addSheet(summarySheet);

        if (emotionalSummarySheet != null) workbook.addSheet(emotionalSummarySheet);

        for (String code: codes) {
            if (wordFrequenciesSheets.containsKey(code)) {
                workbook.addSheet(wordFrequenciesSheets.get(code));
            }
            if (posTagsSheets.containsKey(code)) {
                workbook.addSheet(posTagsSheets.get(code));
            }
            if (coOccurrencesSheets.containsKey(code)) {
                workbook.addSheet(coOccurrencesSheets.get(code));
            }
            if (sentenceSheets.containsKey(code)) {
                workbook.addSheet(sentenceSheets.get(code));
            }
            if (emotionalSheets.containsKey(code)) {
                workbook.addSheet(emotionalSheets.get(code));
            }
        }

        return workbook;
    }

    private ExcelSheet createSummarySheet() {
        ExcelSheet summarySheet = new ExcelSheet();
        summarySheet.setName(
            i18n.getMessage("report.summary", null, LocaleContextHolder.getLocale())
        );
        summarySheet.setHeader(
            i18n.getMessage("report.summary.code", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.task", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.participant", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.characters", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.nonBlankCharacters", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.words", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.distinctwords", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.functionwords", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.distinctfunctionwords", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.contentwords", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.distinctcontentwords", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.distinctlemma", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.avgwordlength", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.avgfunctionwordlength", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.avgcontentwordlength", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.sentences", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.sentenceavgwords", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.lexicaldensity", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.basettr", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.hdd", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.mtld", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.vocd", null, LocaleContextHolder.getLocale()),
            i18n.getMessage("report.summary.ideadensity", null, LocaleContextHolder.getLocale())
        );
        return summarySheet;
    }

    static class WordFrequencyType {
        String word;
        int freq;
        String type;

        public WordFrequencyType(String word, int freq, String type) {
            this.word = word;
            this.freq = freq;
            this.type = type;
        }
    }
}
