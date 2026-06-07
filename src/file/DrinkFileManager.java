package file;

import datastructure.DrinkLinkedList;
import exception.FileDataException;
import model.Drink;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** data/drinks.txt: 음료명/가격/재고/판매수 저장. 없으면 기본 8종 생성. */
public class DrinkFileManager extends FileManager {
    private static final Path PATH = Path.of("data", "drinks.txt");
    public DrinkLinkedList loadDrinks() throws FileDataException {
        List<String> lines = readLines(PATH);
        if (lines.isEmpty()) {
            DrinkLinkedList defaults = defaultDrinks(); saveDrinks(defaults); return defaults;
        }
        DrinkLinkedList list = new DrinkLinkedList();
        try { for (String line : lines) if (!line.isBlank()) list.add(Drink.fromCsv(line)); }
        catch (Exception e) { throw new FileDataException("음료 파일 형식 오류", e); }
        return list;
    }
    public void saveDrinks(DrinkLinkedList list) throws FileDataException {
        List<String> lines = new ArrayList<>(); for (Drink d : list.toArray()) lines.add(d.toCsv()); writeLines(PATH, lines);
    }
    private DrinkLinkedList defaultDrinks() {
        DrinkLinkedList l = new DrinkLinkedList();
        l.add(new Drink(1, "믹스커피", 200, 10, 0)); l.add(new Drink(2, "고급믹스커피", 300, 10, 0));
        l.add(new Drink(3, "물", 450, 10, 0)); l.add(new Drink(4, "캔커피", 500, 10, 0));
        l.add(new Drink(5, "이온음료", 550, 10, 0)); l.add(new Drink(6, "고급캔커피", 700, 10, 0));
        l.add(new Drink(7, "탄산음료", 750, 10, 0)); l.add(new Drink(8, "특화음료", 800, 10, 0));
        return l;
    }
}
