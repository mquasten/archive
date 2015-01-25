package de.mq.archive.web;

import java.io.Serializable;

import org.apache.wicket.model.IModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.mq.archive.domain.Archive;
import de.mq.archive.web.TestComponent.TestComponentInvalid;
import de.mq.archive.web.search.ArchiveModelParts;

public class ComponentFactoryTest {
	
	

	private static final String WICKET_ID = "wicketId";

	public final ComponentFactory componentFactory = new SimpleComponentFactoryImpl();
	
	@SuppressWarnings("unchecked")
	final  IModel<Serializable> partModel = Mockito.mock(IModel.class);
	@SuppressWarnings("unchecked")
	final OneWayMapping<Archive,Enum<?>> model = Mockito.mock(OneWayMapping.class);
	
	
	
	
	@Before()
	public final void setup() {
		Mockito.when(model.part(ArchiveModelParts.Name)).thenReturn(partModel);
	}
	
	@Test
	public final void fromEnum() {

		
		
		final TestComponent result = componentFactory.newComponent(model, ArchiveModelParts.Name, TestComponent.class);
	   
	   Assert.assertEquals(partModel, result.model);
	   Assert.assertEquals(ArchiveModelParts.Name.wicketId(), result.wicketId);
	   Assert.assertNull(result.values);
	}
	
	@Test
	public final void withValues() {

		@SuppressWarnings("unchecked")
		IModel<Serializable> values= Mockito.mock(IModel.class);
		
		
		
		final TestComponent result = componentFactory.newComponent(model, ArchiveModelParts.Name, TestComponent.class, values);
	   
	   Assert.assertEquals(partModel, result.model);
	   Assert.assertEquals(ArchiveModelParts.Name.wicketId(), result.wicketId);
	   Assert.assertEquals(values, result.values);
	}
	
	@Test
	public final void withoutModel() {
		final TestComponent result = componentFactory.newComponent(WICKET_ID,partModel,TestComponent.class);
		 Assert.assertEquals(partModel, result.model);
		 Assert.assertEquals(WICKET_ID, result.wicketId);
		 Assert.assertNull(result.values);
	}
	
	@Test(expected=IllegalStateException.class)
	public final void withException() {
	
		componentFactory.newComponent(TestComponent.WICKET_ID_EXCEPTION,partModel,TestComponentInvalid.class);
	}

	
}

class TestComponent {
	static final String WICKET_ID_EXCEPTION = "exception";
	final IModel<Serializable> model;
	final String wicketId;
	final IModel<Serializable> values; 
	
	
	
	
	public TestComponent(final String wicketId, final IModel<Serializable>  model ) {
		this(wicketId, model, null);
	}
	

	
	public TestComponent(final String wicketId, final IModel<Serializable>  model, final IModel<Serializable>  values ) {
		this.wicketId=wicketId;
		this.model=model;
		this.values=values;
		if( wicketId.equalsIgnoreCase(WICKET_ID_EXCEPTION)) {
			 throw new RuntimeException("Don't worry only for test");
		}
	}
	
	class TestComponentInvalid {
		
	}
}


