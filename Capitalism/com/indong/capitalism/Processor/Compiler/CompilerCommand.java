package com.indong.capitalism.Processor.Compiler;

import java.util.LinkedList;

public abstract class CompilerCommand {
	protected LinkedList<String> commandList;
	
	public CompilerCommand(LinkedList<String> list)
	{
		commandList = list;
	}
	
	abstract public boolean compile();
}
