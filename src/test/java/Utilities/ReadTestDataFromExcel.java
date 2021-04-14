package Utilities;

import java.util.Arrays;
import java.util.List;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ReadTestDataFromExcel {

	public Object[][] testFillo() throws FilloException {
		Object[][] excelData = null;
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(System.getProperty("user.dir") + "/testData/test.xlsx");
		String strQuery = "Select * from Sheet1";
		Recordset rs = connection.executeQuery(strQuery);

		System.out.println("total rows = " + rs.getCount());
		List<String> fieldNames = rs.getFieldNames();
		int rows = rs.getCount();
		int columns = fieldNames.size();
		int i = 0;
		excelData = new Object[rows][columns];
		while (rs.next()) {
			int j = 0;
			for (String fieldName : fieldNames) {
				System.out.print(rs.getField(fieldName) + " | ");
				if (fieldName != null) {
					excelData[i][j] = rs.getField(fieldName);
					j++;
				}
			}
			i++;
			System.out.println();

		}

		rs.close();
		connection.close();
		System.out.println("excelData = " + Arrays.deepToString(excelData));
		return excelData;
	}
}
