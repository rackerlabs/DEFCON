package com.rackspace.it

import java.lang.IllegalArgumentException

public enum Color {
    ALL("all", 1, ""), RED("red", 2, "00"), YELLOW("yellow", 3, "01"), BLUE("blue", 4, "02"), GREEN("green", 5, "03"), CLEAR("clear", 6, "")
    
    final String text
	final Integer code
    final String plc_code

    Color(String color, Integer code, String plc_code){
        this.text = color
        this.code = code
		this.plc_code = plc_code
    }
    
    String toString() { this.text }
    
    static get(String color){
        switch(color?.toLowerCase()){
            case "clear":
                return Color.CLEAR
            case "green":
                return Color.GREEN
            case "blue": 
                return Color.BLUE
            case "yellow":
                return Color.YELLOW
            case "red":
                return Color.RED
            case "all":
                return Color.ALL
			default:
               // return Color.CLEAR
			     throw new IllegalArgumentException("'$color' is an invalid color")
        }
    }	
}