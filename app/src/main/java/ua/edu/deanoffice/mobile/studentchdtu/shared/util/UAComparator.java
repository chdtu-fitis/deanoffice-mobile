package ua.edu.deanoffice.mobile.studentchdtu.shared.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class UAComparator implements Comparator<String> {
    public int compare(String str1, String str2) {
        Collator uaCollator = Collator.getInstance(new Locale("uk", "UA"));
        return uaCollator.compare(str1, str2);
    }
}
