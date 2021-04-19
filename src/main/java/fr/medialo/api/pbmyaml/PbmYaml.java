package fr.medialo.api.pbmyaml;

import org.apache.commons.lang.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PbmYaml {

    private final DumperOptions yamlOptions = new DumperOptions();
    private final Yaml yaml;
    private Map<String, Object> values;
    private File file;
    private List<String> footer;
    private List<String> header;

    public PbmYaml() {
        yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yamlOptions.setPrettyFlow(true);
        this.yaml = new Yaml(yamlOptions);
    }

    public void load(File file) {
        this.file = file;
        try (BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file))) {
            this.values = this.yaml.load(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(this.values == null)
            this.values = new HashMap<>();
    }

    public void clearFooter() {
        this.footer.clear();
    }

    public void setFooter(List<String> list) {
        this.footer = list;
    }

    public void addFooter(String... str) {
        if (this.footer == null)
            this.footer = new ArrayList<>();
        this.footer.addAll(Arrays.asList(str));
    }


    public void clearHeader() {
        this.header.clear();
    }

    public void setheader(List<String> list) {
        this.header = list;
    }

    public void addHeader(String... str) {
        if (this.header == null)
            this.header = new ArrayList<>();
        this.header.addAll(Arrays.asList(str));
    }


    public void createFileAndParents(File file) {
        createFileAndParents(file.getParentFile().toPath(), file.getName());
    }

    public void createFileAndParents(File file, String fileName) {
        createFileAndParents(file.toPath(), fileName);
    }

    public void createFileAndParents(File getDataFolder, String parentsFolder, String fileName) {
        createFileAndParents(new File(getDataFolder, parentsFolder).toPath(), fileName);
    }

    public void createFileAndParents(Path path, String fileName) {
        try {
            if (!Files.exists(path))
                Files.createDirectories(path);
            if (!Files.exists(path.resolve(fileName)))
                Files.createFile(path.resolve(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        load(this.file);
    }

    public void save() {
        Map<String, List<String>> commentsList = new HashMap<>();
        List<String> lineToWrite = null;
        try {
            List<String> footer = new ArrayList<>();
            List<String> commentsTemp = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\b.+[:]");
            Matcher matcher;
            List<String> lines = Files.readAllLines(this.file.toPath());
            for (String line : lines) {
                String ll = line.trim();
                matcher = pattern.matcher(ll);
                if (ll.startsWith("#")) {
                    commentsTemp.add(ll);
                } else if (matcher.find() && !commentsTemp.isEmpty()) {
                    commentsList.put(StringUtils.chop(matcher.group(0)), new ArrayList<>(commentsTemp));
                    System.out.println(commentsTemp.size());
                    commentsTemp.clear();
                }
            }
            if (!commentsTemp.isEmpty()) {
                footer = commentsTemp;
            }
//-------------------------------------------------------------
            lineToWrite = new ArrayList<>(Arrays.asList(yaml.dump(this.values).split("\n")));
            for (int i = 0; i < lineToWrite.size(); i++) {
                String line = lineToWrite.get(i);
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String key = StringUtils.chop(matcher.group(0));
                    List<String> commentsLines = commentsList.get(key);
                    if (commentsLines != null) {
                        lineToWrite.addAll(i, commentsLines);
                        commentsList.remove(key);
                        i += commentsLines.size() - 1;
                    }
                }
            }
            lineToWrite.addAll(footer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lineToWrite != null) {
            if (this.header != null) {
                this.header.replaceAll(this::commentCheck);
                lineToWrite.addAll(0, this.header);
            }
            if (this.footer != null) {
                this.footer.replaceAll(this::commentCheck);
                lineToWrite.addAll(this.footer);
            }
            try {
                Files.write(file.toPath(), lineToWrite, Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Object urlToObj(String url) {
        String[] urls = url.split("\\.");
        if (urls.length <= 1) {
            return this.values.get(url);
        } else {
            Map<String, Object> tempMap = null;
            for (int i = 0; i < urls.length - 1; i++) {
                if (tempMap == null) {
                    tempMap = (Map<String, Object>) this.values.get(urls[i]);
                } else {
                    tempMap = (Map<String, Object>) tempMap.get(urls[i]);
                }
            }
            return tempMap.get(urls[urls.length - 1]);
        }
    }

    /**
     * This method is not ready to be used !
     */
    @Deprecated
    public void remove(String url) {
        this.values.remove(url);
    }

    public boolean getBoolean(String url) {
        return urlToObj(url) != null && (boolean) urlToObj(url);
    }

    public float getFloat(String url) {
        return urlToObj(url) == null ? 0.0f : (float) urlToObj(url);
    }

    public double getDouble(String url) {
        return urlToObj(url) == null ? 0.0 : (double) urlToObj(url);
    }


    public char getChar(String url) {
        return urlToObj(url) == null ? '0' : (char) urlToObj(url);
    }

    public byte getByte(String url) {
        return urlToObj(url) == null ? 0 : (byte) urlToObj(url);
    }

    public short getShort(String url) {
        return urlToObj(url) == null ? 0 : (short) urlToObj(url);
    }

    public int getInt(String url) {
        return urlToObj(url) == null ? 0 : (int) urlToObj(url);
    }

    public long getLong(String url) {
        return urlToObj(url) == null ? 0 : (long) urlToObj(url);
    }

    public String getString(String url) {
        return urlToObj(url) == null ? "" : (String) urlToObj(url);
    }

    public List<?> getList(String url) {
        return urlToObj(url) == null ? Collections.emptyList() : (List<?>) urlToObj(url);
    }

    public <T> T getTypeOrDefault(String url, T def) {
        return urlToObj(url) == null ? def : (T) urlToObj(url);
    }

    public boolean getBooleanOrDefault(String url, boolean def) {
        return urlToObj(url) == null ? def : (boolean) urlToObj(url);
    }

    public float getFloatOrDefault(String url, float def) {
        return urlToObj(url) == null ? def : (float) urlToObj(url);
    }

    public double getDoublerDefault(String url, double def) {
        return urlToObj(url) == null ? def : (double) urlToObj(url);
    }

    public char getCharOrDefault(String url, char def) {
        return urlToObj(url) == null ? def : (char) urlToObj(url);
    }

    public byte getByteOrDefault(String url, byte def) {
        return urlToObj(url) == null ? def : (byte) urlToObj(url);
    }

    public short getShortOrDefault(String url, short def) {
        return urlToObj(url) == null ? def : (short) urlToObj(url);
    }

    public int getIntOrDefault(String url, int def) {
        return urlToObj(url) == null ? def : (int) urlToObj(url);
    }

    public long getLongOrDefault(String url, long def) {
        return urlToObj(url) == null ? def : (long) urlToObj(url);
    }

    public String getStringOrDefault(String url, String def) {
        return urlToObj(url) == null ? def : (String) urlToObj(url);
    }

    public List<?> getListOrDefault(String url, List<?> def) {
        return urlToObj(url) == null ? def : (List<?>) urlToObj(url);
    }


    public <T> T getTypeOrDefaultAndSetSave(String url, T def) {
        Object bool = urlToObj(url);
        if (bool == null) {
            set(url, def);
            save();
            return def;
        } else {
            return (T) urlToObj(url);
        }
    }

    @Deprecated
    public Object getObject(String url) {
        return this.values.get(url);
    }

    private String commentCheck(String str) {
        return (!str.startsWith("#")) ? "#" + str : str;
    }

    private String urlCheck(String str) {
        return (!str.endsWith(".")) ? str + "." : str;
    }

    public void customSerializer(PbmSerializable obj) {
        addValues("", obj.getSerializedObject());
    }

    public void customSerializer(String url, PbmSerializable obj) {
        addValues(url, obj.getSerializedObject());
    }

    public void set(String url, Object value) {
        if (this.values == null)
            this.values = new HashMap<>();
        String[] urls = url.split("\\.");
        if (!url.isEmpty()) {
            Map<String, Object> mapTemp = null;
            if (urls.length < 2) {
                this.values.put(url, value);
            } else {
                for (int i = urls.length - 1; i > 0; i--) {
                    Map<String, Object> mapfor = new HashMap<>();
                    if (i == urls.length - 1) {
                        mapfor.put(urls[i], value);
                    } else {
                        mapfor.put(urls[i], mapTemp);
                    }
                    mapTemp = mapfor;
                }
                this.values.put(urls[0], mapTemp);
            }
        }
    }

    public void addValues(String url, Map<String, Object> values) {
        if (this.values == null)
            this.values = new HashMap<>();
        String[] urls = url.split("\\.");
        if (!url.isEmpty()) {
            Map<String, Object> mapTemp = null;
            if (urls.length < 2) {
                this.values.put(url, values);
            } else {
                for (int i = urls.length - 1; i > 0; i--) {
                    Map<String, Object> mapfor = new HashMap<>();
                    if (i == urls.length - 1) {
                        mapfor.put(urls[i], values);
                    } else {
                        mapfor.put(urls[i], mapTemp);
                    }
                    mapTemp = mapfor;
                }
                this.values.put(urls[0], mapTemp);
            }
        } else {
            this.values.putAll(values);
        }
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
