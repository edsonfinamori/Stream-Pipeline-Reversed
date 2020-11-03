package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter full file path: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			List<Employee> list = new ArrayList<>();
			
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				String name = fields[0];
				String email = fields[1];
				double salary = Double.parseDouble(fields[2]);
				Employee emp = new Employee(name, email, salary);
				list.add(emp);
				
				line = br.readLine();
			}
			
			System.out.print("Enter salary: ");
			double salaryLimit = sc.nextDouble();
			
			Comparator<String> comp = (s1, s2) -> s1.toLowerCase().compareTo(s2.toLowerCase());
			
			List<String> emails = list.stream()
					.filter(s -> s.getSalary() > salaryLimit)
					.map(e -> e.getEmail())
					.sorted(comp.reversed())
					.collect(Collectors.toList());
			
			System.out.println("Email of people whose salary is more than " + String.format("%.2f", salaryLimit) + ":");
			emails.forEach(System.out::println);
			
			double sumSal = list.stream()
					.filter(e -> e.getName().charAt(0) == 'M')
					.map(e -> e.getSalary())
					.reduce(0.0, (x,y) -> x+y);
			
			System.out.print("Sum of salary of people whose name starts with 'M': " + String.format("%.2f", sumSal));
			
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		sc.close();
	}

}