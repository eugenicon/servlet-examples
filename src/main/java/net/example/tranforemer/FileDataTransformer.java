package net.example.tranforemer;

import net.example.data.model.FileData;
import net.example.resolver.Component;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
public class FileDataTransformer implements Transformer<FileData> {
    @Override
    public FileData transform(HttpServletRequest request, String parameter) {
        try {
            FileData fileData = new FileData();
            if (ServletFileUpload.isMultipartContent(request)) {
                ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
                Map<String, List<FileItem>> parameters = fileUpload.parseParameterMap(request);
                String id = parameters.get("id").get(0).getString();
                fileData.setId(id.matches("\\d+") ? Integer.parseInt(id) : 0);
                FileItem file = parameters.get("file").get(0);
                fileData.setName(file.getName());
                fileData.setData(file.get());
                fileData.setSize(fileData.getData().length);
                return fileData;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Class<FileData> getSupportedType() {
        return FileData.class;
    }
}
