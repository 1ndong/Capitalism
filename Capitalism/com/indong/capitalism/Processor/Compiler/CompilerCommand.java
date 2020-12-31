package com.indong.capitalism.Processor.Compiler;

import java.util.LinkedList;

public class CompilerCommand {
	protected LinkedList<String> commandList;
	
	public CompilerCommand(LinkedList<String> list)
	{
		commandList = list;
	}
	
	public boolean compile()
	{
		return false;
	}
}
