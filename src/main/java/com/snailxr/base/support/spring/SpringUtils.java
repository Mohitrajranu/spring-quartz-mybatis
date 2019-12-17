package com.snailxr.base.support.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class SpringUtils.
 */
public final class SpringUtils implements BeanFactoryPostProcessor {

	/** The bean factory. */
	private static ConfigurableListableBeanFactory beanFactory; // Spring application context

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringUtils.beanFactory = beanFactory;
	}

	/**
	 * Get object.
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @return Object 一An instance of a bean registered with the given name
	 * @throws BeansException the beans exception
	 * @throws org.springframework.beans.BeansException the org.springframework.beans. beans exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) beanFactory.getBean(name);
	}

	/**
	 * Get an object of type requiredType.
	 *
	 * @param <T> the generic type
	 * @param clz the clz
	 * @return the bean
	 * @throws BeansException the beans exception
	 * @throws org.springframework.beans.BeansException the org.springframework.beans. beans exception
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException {
		@SuppressWarnings("unchecked")
		T result = (T) beanFactory.getBean(clz);
		return result;
	}

	/**
	 * Return true if the BeanFactory contains a bean definition that matches the given name.
	 *
	 * @param name the name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return beanFactory.containsBean(name);
	}

	/**
	 * Determines whether the bean definition registered with the given name is a singleton or a prototype.
	 * If the bean definition corresponding to the given name is not found, an exception will be thrown (NoSuchBeanDefinitionException)
	 *
	 * @param name the name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException the no such bean definition exception
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException the org.springframework.beans.factory. no such bean definition exception
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isSingleton(name);
	}

	/**
	 * Gets the type.
	 *
	 * @param name the name
	 * @return Class Type of registered object
	 * @throws NoSuchBeanDefinitionException the no such bean definition exception
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException the org.springframework.beans.factory. no such bean definition exception
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getType(name);
	}

	/**
	 * Returns the alias if the given bean name has an alias in the bean definition.
	 *
	 * @param name the name
	 * @return the aliases
	 * @throws NoSuchBeanDefinitionException the no such bean definition exception
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException the org.springframework.beans.factory. no such bean definition exception
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getAliases(name);
	}

}
