@regression @egressToNetApp @ui
Feature: Transfer files between Egress and NetApp

  As an automation user
  I want to transfer files between Egress and NetApp
  So that files are copied or moved successfully while ensuring
  the required Case and Workspace exist in the target system

  @EgressToNetAppCopy @EgressToNetAppCopyCaseWorkspaceExists
  Scenario: Copy a file from Egress to NetApp when Case and Workspace exist
    Given a file "test-file.txt" exists in Egress
    And the transfer operation is "copy"
    And the target Case "CASE-001" exists in NetApp
    And the target Workspace "Workspace-001" exists in Case "CASE-001"
    When I transfer the file from Egress to NetApp
    Then the file "test-file.txt" should exist in Workspace "Workspace-001" of Case "CASE-001" in NetApp
    And the source file should still exist in Egress