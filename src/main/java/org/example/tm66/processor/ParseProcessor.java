package org.example.tm66.processor;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.tm66.model.RowDto;
import org.example.tm66.model.Headers;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ParseProcessor {

    @Getter
    public final Map<Headers, Integer> headerPosition = new EnumMap<>(Headers.class);

    public void init(Row headers) {
        int pos = 0;
        for (Cell cell : headers) {
            for (Headers header : Headers.values()) {
                if (cell.toString().equals(header.getText())) {
                    headerPosition.put(header, pos);
                    break;
                }
            }
            pos++;
        }
    }

    public List<RowDto> parse(Sheet sheet) {
        List<RowDto> rows = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            RowDto rowDto = new RowDto();
            for (Map.Entry<Headers, Integer> entry : headerPosition.entrySet()) {
                Cell cell = row.getCell(entry.getValue());
                rowDto.set(entry.getKey(), cell);
            }
            rows.add(rowDto);
        }
        return rows;
    }

}
