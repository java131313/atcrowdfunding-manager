package com.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)

public class Book {
	private  @Setter@Getter Integer id;
	private  @Setter String  name;
	private  double  price;
	private  String  author;
}
