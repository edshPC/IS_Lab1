package com.edsh.is_lab1.dto;

import io.minio.Result;
import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class FileDTO {
    private String name;
    private long size;
    private ZonedDateTime lastModified;

    @SneakyThrows
    public static FileDTO fromItemResult(Result<Item> result) {
        var item = result.get();
        return new FileDTO(item.objectName(), item.size(), item.lastModified());
    }

}
