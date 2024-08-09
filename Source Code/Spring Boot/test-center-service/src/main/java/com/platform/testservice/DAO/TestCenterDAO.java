package com.platform.testservice.DAO;

import com.platform.testservice.DTO.CredentialsDTO;
import com.platform.testservice.DTO.TestCenterUpdateDTO;
import com.platform.testservice.Entity.Branch;
import com.platform.testservice.Entity.TestCenter;

public interface TestCenterDAO {

    TestCenter getByName(String testCenterName);

    int getIdByName(String name);

    String addGeneratedCredentials(CredentialsDTO credentialsDTO);

    CredentialsDTO getGeneratedCredential();

    String removeGeneratedCredentials(CredentialsDTO credentialsDTO);

    String addTestCenterCredentials(CredentialsDTO credentialsDTO);

    String editTestCenterInfo(int testCenterId, TestCenterUpdateDTO testCenterUpdateDTO);

    String addTestCenterBranchInfo(Branch branch);

    Branch getTestCenterByLocation(String locationName);
}
