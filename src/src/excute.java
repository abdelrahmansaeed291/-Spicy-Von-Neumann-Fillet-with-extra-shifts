package src;

import java.util.LinkedList;
import java.util.Queue;

//import sun.jvm.hotspot.tools.SysPropsDumper;

public class excute {
	int cycle = 1;
	Queue<Integer> If;
	Queue<instruction> Id;
	Queue<instruction> Ex;
	Queue<instruction> Men;
	Queue<instruction> Web;
	memory m;
	registers re;

	public excute(memory m, registers regi) {
		this.m = m;
		this.re = regi;
		If = new LinkedList<>();
		Id = new LinkedList<>();
		Ex = new LinkedList<>();
		Men = new LinkedList<>();
		Web = new LinkedList<>();
	}

	public void excute1() {
		int numCycles = 7 + ((m.count-1) * 2);
		int numOfInst = 0;
		// fetch
		while (cycle <= numCycles) {
			int instruction = 0;
			if (cycle % 2 == 1 && numOfInst <= m.count) {
				fetch();
				numOfInst++;
			//	System.out.println("fetch cycle: " + cycle);
			}
			// decode
			if (cycle % 2 == 0 && !If.isEmpty()) {

				decode();
			//	System.out.println("decode cycle: " + cycle);
			}
			if (cycle % 2 == 0 && cycle > 3 && !Id.isEmpty()) {
				excute2();
			//	System.out.println("execute cycle: " + cycle);
			}
			// memory
			if (cycle % 2 == 0 && cycle > 5 && !Ex.isEmpty()) {
				memory();
			//	System.out.println("memory cycle: " + cycle);
			}
			// writeback
			if (cycle % 2 == 1 && cycle > 6 && !Men.isEmpty()) {
				writeback();
			//	System.out.println("wb cycle: " + cycle);
			}
			cycle++;

		}
		System.out.println("cycles "+ (cycle- 1));
	}

	public void fetch() {

		long instruction = 0;

		instruction = m.memory[re.pc];
		
		System.out.println("instraction :- " + readFromFile.in.get(instruction) + " is fetching now at cycle :- " + cycle );
        System.out.println("in fetch take pc :-  " + re.pc +"  instraction :- "+ instruction  );
        
		re.pc++;
		If.add((int) instruction); 
	}

	public void decode() {
		int instruction = If.poll();
		instruction inst = new instruction(instruction);
		inst.opcode = instruction & 0b11110000000000000000000000000000;
		inst.opcode = inst.opcode >>> 28;    //updated was 26 
		inst.R1 = instruction & 0b00001111100000000000000000000000;
		inst.R1 = inst.R1 >> 23;
		inst.R2 = instruction & 0b00000000011111000000000000000000;
		inst.R2 = inst.R2 >> 18;
		inst.R3 = instruction & 0b00000000000000111110000000000000;
		inst.R3 = inst.R3 >> 13;
		inst.shamt = instruction & 0b00000000000000000001111111111111;
//		inst.shamt = inst.shamt >> 6;
		inst.imm = instruction & 0b00000000000000111111111111111111;
		inst.address = instruction & 0b00001111111111111111111111111111;
		Id.add(inst);
	//	System.out.println("decode : "+inst.R1+"   "+inst.R2+"   "+inst.R3);
  // System.out.println("opp in decode is "  +  inst.opcode );
   System.out.println("instraction :- " + readFromFile.in.get(inst.all) + " is decoding now at cycle :- " + cycle );
   System.out.println("in decoding take instraction :- " + readFromFile.in.get(inst.all) + "  and decoded to :- " + Integer.toBinaryString(instruction));

	}

	public void excute2() {
		instruction inst = Id.poll();
		  System.out.println("instraction :- " + readFromFile.in.get(inst.all) + " is excuting now at cycle :- " + cycle );

		switch (inst.opcode) {
		case 0:
			inst.result = re.generalregister[ inst.R2] + re.generalregister[inst.R3];
			inst.writeback = 1;
			System.out.println("instraction number :- " + " 0 " + " , inputs :- " + " R" + inst.R2 + " = "+re.generalregister[ inst.R2]  + ", R" + inst.R3 + " = "+re.generalregister[ inst.R3] + "  and the result =  " + inst.result );
			break;
		case 1:
			inst.result = re.generalregister[ inst.R2] - re.generalregister[inst.R3];
			inst.writeback = 1;
			System.out.println("instraction number :- " + " 1 " + " , inputs :- " + " R" + inst.R2 + " = "+re.generalregister[ inst.R2]  + ", R" + inst.R3 + " = "+re.generalregister[ inst.R3] + "  and the result =  " + inst.result );
			
			break;
		case 2:
			inst.result = re.generalregister[ inst.R2] * inst.imm;
			inst.writeback = 1;
			System.out.println("instraction number :- " + " 2 " + "inputs :- " + " R" + inst.R2 + " = "+re.generalregister[ inst.R2]  + " and imm = " + inst.imm  + "and the result =  " + inst.result );
			
			break;
		case 3:
			inst.result = re.generalregister[ inst.R2] + inst.imm;
			inst.writeback = 1;
			System.out.println("instraction number :- " + " 3 " + "inputs :- " + " R" + inst.R2 + " = "+re.generalregister[ inst.R2]  + " and imm = " + inst.imm  + "and the result =  " + inst.result );
			
			break;
		case 4:
			if (inst.R1 != inst.R2) {
				re.pc = (re.pc + 1 + inst.imm);
			}
			;
			System.out.println("instraction number :- " + " 4 " + " , inputs :- " + " R" + inst.R1 + " = "+re.generalregister[ inst.R1]  + ", R" + inst.R2 + " = "+re.generalregister[ inst.R2] + ", the imm =  "+ inst.imm + "  and the new address =  " + re.pc );
			
			break;
		case 5:
			inst.result = re.generalregister[inst.R2] & inst.imm;
			System.out.println("instraction number :- " + " 5 " + "inputs :- " + " R" + inst.R2 + " = "+re.generalregister[ inst.R2]  + " and imm = " + inst.imm  + "and the result =  " + inst.result );
			
			inst.writeback = 1;
			break;
		case 6:
			inst.result = re.generalregister[inst.R2] | inst.imm;
			inst.writeback = 1;
			System.out.println("instraction number :- " + " 6 " + "inputs :- " + " R" + inst.R2 + " = "+re.generalregister[ inst.R2]  + " and imm = " + inst.imm  + "and the result =  " + inst.result );
			
			break;
		case 7:
			int px = re.pc & 0b11110000000000000000000000000000;
			re.pc =  (px | inst.address);
			System.out.println("instraction number :- " + " 7 " + "inputs :- " + " address = " + inst.address + " and the new pc = " +re.pc );
			break;
		case 8:
			inst.result = re.generalregister[inst.R2] << inst.shamt;
			inst.writeback = 1;
			System.out.println("instraction number :- " + " 8 " + "inputs :- " + " R" + inst.R2 + " = "+re.generalregister[ inst.R2]  + " and shamt = " + inst.shamt  + "and the result =  " + inst.result );
			
			break;
		case 9:
			inst.result = re.generalregister[inst.R2] >> inst.shamt;
			inst.writeback = 1;
			System.out.println("instraction number :- " + " 9 " + "inputs :- " + " R" + inst.R2 + " = "+re.generalregister[ inst.R2]  + " and shamt = " + inst.shamt  + "and the result =  " + inst.result );
			
			break;
		case 10:
			inst.result = re.generalregister[inst.R2] + inst.imm;
			inst.writeback = 1;
			System.out.println("instraction number :- " + " 10 " + "inputs :- " + " R" + inst.R2 + " = "+re.generalregister[ inst.R2]  + " and imm = " + inst.imm  + "and the result =  " + inst.result );
			
			break;
		case 11:
			inst.result = re.generalregister[inst.R2] + inst.imm;
			System.out.println("instraction number :- " + " 11 " + "inputs :- " + " R" + inst.R2 + " = "+re.generalregister[ inst.R2]  + " and imm = " + inst.imm  + "and the result =  " + inst.result );
			
			
			break;
			default:break;
			

		}
	//	System.out.println("op excuting is " + inst.opcode);
	
	
		Ex.add(inst);

	}

	public void memory() {
		instruction inst = Ex.poll();
		if (inst.opcode == 10) {
			inst.result = m.memory[(int) inst.result];
		} else if (inst.opcode == 11) {
			m.memory[(int)inst.result] = re.generalregister[(int) inst.R1];
			  System.out.println("value of memory in place "+ inst.result + " changed to " + m.memory[(int)inst.result]);
			
		}
		Men.add(inst);
		
		  System.out.println("instraction :- " + readFromFile.in.get(inst.all) + " is accessing memory now at cycle :- " + cycle );

	}

	public void writeback() {
		instruction inst = Men.poll();
		if (inst.writeback == 1 && inst.R1 != 0) {
			re.generalregister[(int) inst.R1] = (int) inst.result;
		}
	//	System.out.println("wwwwwwwwww");
		
		  System.out.println("instraction :- " + readFromFile.in.get(inst.all) + " is writing back now at cycle :- " + cycle );
		  System.out.println("value of register  R"+ inst.R1 + " changed to " + inst.result);

	}

}
