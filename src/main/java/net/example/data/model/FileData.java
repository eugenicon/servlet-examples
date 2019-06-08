package net.example.data.model;

public class FileData {
    private int id;
    private String name;
    private byte[] data;
    private String encoded;
    private long size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public String getPrettySize() {
        int unit = 1024;
        if (size < unit) {
            return size + " B";
        }
        int exp = (int) (Math.log(size) / Math.log(unit));
        char pre = ("kMGTPE").charAt(exp - 1);
        return String.format("%.1f %sB", size / Math.pow(unit, exp), pre);
    }
}
