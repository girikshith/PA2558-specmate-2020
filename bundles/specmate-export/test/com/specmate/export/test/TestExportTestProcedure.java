package com.specmate.export.test;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import com.specmate.export.internal.exporters.CSVTestProcedureExporter;
import com.specmate.model.export.Export;
import com.specmate.model.testspecification.TestProcedure;
import com.specmate.model.testspecification.TestStep;
import com.specmate.model.testspecification.TestspecificationFactory;

public class TestExportTestProcedure {

	@Test
	public void testCSVExport_FilledTestProcedure() {
		TestProcedure tp = getTestProcedure();

		CSVTestProcedureExporter exporter = new CSVTestProcedureExporter();
		Optional<Export> result = exporter.export(tp);
		String code = result.get().getContent();
		Assert.assertEquals("Step Name;Action;Expected Outcome\n" + "step-1;Description1;Outcome1\n"
				+ "step-2;Description2;Outcome2", code);
	}

	@Test
	public void testCSVExport_EmptyTestProcedure() {
		TestProcedure tp = getTestProcedure();
		tp.getContents().clear();
		CSVTestProcedureExporter exporter = new CSVTestProcedureExporter();
		Optional<Export> result = exporter.export(tp);
		String code = result.get().getContent();
		Assert.assertEquals("Step Name;Action;Expected Outcome", code);
	}

	private TestProcedure getTestProcedure() {
		TestspecificationFactory f = TestspecificationFactory.eINSTANCE;
		TestProcedure tp = f.createTestProcedure();
		tp.setId("tp-id-1");
		tp.setName("TP");

		TestStep step1 = f.createTestStep();
		step1.setId("step-id-2");
		step1.setName("step-1");
		step1.setDescription("Description1");
		step1.setExpectedOutcome("Outcome1");
		tp.getContents().add(step1);

		TestStep step2 = f.createTestStep();
		step2.setId("step-id-3");
		step2.setName("step-2");
		step2.setDescription("Description2");
		step2.setExpectedOutcome("Outcome2");
		tp.getContents().add(step2);

		return tp;
	}
}