package net.example.tranforemer;

import net.example.data.model.FileData;
import net.example.resolver.Component;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;

@Component
public class FileDataTransformer implements Transformer<FileData> {
    private final IntegerTransformer integerTransformer;
    private final FileItemTransformer fileItemTransformer;

    public FileDataTransformer(IntegerTransformer integerTransformer, FileItemTransformer fileItemTransformer) {
        this.integerTransformer = integerTransformer;
        this.fileItemTransformer = fileItemTransformer;
    }

    @Override
    public FileData transform(HttpServletRequest request, String parameter) {
        FileData fileData = new FileData();
        fileData.setId(integerTransformer.transform(request, "id"));
        FileItem file = fileItemTransformer.transform(request,"file")[0];
        fileData.setName(file.getName());
        fileData.setData(file.get());
        fileData.setSize(fileData.getData().length);
        return fileData;
    }

    @Override
    public Class<FileData> getSupportedType() {
        return FileData.class;
    }
}
