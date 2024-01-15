package com.juneox.marketspace.test.utils;

import com.juneox.marketspace.utils.CsvUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class CsvUtilsTests {

    @Test
    void readCsvTest(){
        //given
        List<List<? extends Serializable>> expectedList =
                Arrays.asList(Arrays.asList(20211,"D","발달상권","3120070","한성대입구역","CS300009","청과상",3,3,0,0,0,0,0));

        //when
        List<List<String>> lists = CsvUtils.readCsv("./sample/sample2021limit60.csv");

        //then
        Assertions.assertEquals(expectedList.get(0).get(3), lists.get(1).get(3));
    }
}
