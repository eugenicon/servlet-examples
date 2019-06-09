package net.example.tranforemer;

import net.example.resolver.Component;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;

@Component
public class FileItemTransformer implements Transformer<FileItem[]> {
    @Override
    public FileItem[] transform(HttpServletRequest request, String parameter) {
        FileItem[] items = (FileItem[]) request.getAttribute(parameter);
        return items == null ? new FileItem[0] : items;
    }

    @Override
    public Class<FileItem[]> getSupportedType() {
        return FileItem[].class;
    }
}
