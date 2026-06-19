package com.example.studysystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsVO {
    private List<NameValueVO> categoryStats;
    private List<NameValueVO> monthStats;
    private List<NameValueVO> statusStats;
}
