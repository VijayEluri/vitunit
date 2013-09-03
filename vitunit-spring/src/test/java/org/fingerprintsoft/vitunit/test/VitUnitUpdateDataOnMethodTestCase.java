/*
 * VitUnitTestCase.java
 *
 * Copyright (C) 2010 Fingerprints Software
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.fingerprintsoft.vitunit.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fingerprintsoft.vitunit.annotation.DataSetConfiguration;
import com.fingerprintsoft.vitunit.annotation.DataSourceConfiguration;
import com.fingerprintsoft.vitunit.runner.VitUnitSpringJUnit4Runner;

/**
 * 
 * @author <a href=mailto:fuzail@fingerprintsoft.org>Fuzail Sarang</a>
 * 
 */
@RunWith(VitUnitSpringJUnit4Runner.class)
@DataSetConfiguration(dbunitProperties = "dbunit.properties")
@DataSourceConfiguration(jdbcPropertiesFile = "jdbc.properties")
public class VitUnitUpdateDataOnMethodTestCase extends VitUnitTestCase {

    @Test
    @DataSetConfiguration(insertDatasets = "target/test-classes/insert-update-data.xml", updateDatasets = "target/test-classes/update-data.xml", deleteAllDataSets = "target/test-classes/delete-data.xml")
    public void testUpdate() {
	DataSource dataSource = loadDatasource(this.getClass());
	Connection connection = null;
	try {

	    connection = dataSource.getConnection();
	    PreparedStatement prepareStatement = connection
		    .prepareStatement("select * from tbl_data_sets where id = -3");
	    ResultSet resultSet = prepareStatement.executeQuery();
	    Assert.assertTrue(resultSet.next());
	    Assert.assertFalse(resultSet.next());
	} catch (SQLException e) {
	    Assert.fail(e.getLocalizedMessage());
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
	    } catch (NullPointerException e2) {
	    }
	}
    }
    
    @AfterClass
    public static void check() {
	DataSource dataSource = loadDatasource(VitUnitUpdateDataOnMethodTestCase.class);
	Connection connection = null;
	try {

	    connection = dataSource.getConnection();
	    PreparedStatement prepareStatement = connection
		    .prepareStatement("select * from tbl_data_sets where id = -3");
	    ResultSet resultSet = prepareStatement.executeQuery();
	    Assert.assertFalse(resultSet.next());
	} catch (SQLException e) {
	    Assert.fail(e.getLocalizedMessage());
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
	    } catch (NullPointerException e2) {
	    }
	}
    }

}
