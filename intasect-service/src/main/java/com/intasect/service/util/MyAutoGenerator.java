package com.intasect.service.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;

import java.util.List;
import java.util.Set;

/**
 * 自定义策略
 */
class MyAutoGenerator extends AutoGenerator {

	@Override
	protected List<TableInfo> getAllTableInfoList(ConfigBuilder config) {
		List<TableInfo> allTableInfoList = super.getAllTableInfoList(config);
		allTableInfoList.forEach(tableInfo -> {
			List<TableField> fields = tableInfo.getFields();
			Set<String> importPackages = tableInfo.getImportPackages();
			fields.forEach(field -> {
				// 如果存在LocalDateTime类型时，将其修改为Date类型
				if (field.getPropertyType().equals("LocalDateTime")) {
					field.setColumnType(DbColumnType.DATE);
					importPackages.remove("java.time.LocalDateTime");
					importPackages.add("java.util.Date");
				}
				if (field.getPropertyType().equals("LocalDate")) {
					field.setColumnType(DbColumnType.DATE);
					importPackages.remove("java.time.LocalDate");
					importPackages.add("java.util.Date");
				}
			});
		});
		return allTableInfoList;
	}

}