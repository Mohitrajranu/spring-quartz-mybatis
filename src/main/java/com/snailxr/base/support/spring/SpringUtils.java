package com.snailxr.base.support.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public final class SpringUtils implements BeanFactoryPostProcessor {

	private static ConfigurableListableBeanFactory beanFactory; // Spring application context

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringUtils.beanFactory = beanFactory;
	}

	/**
	 * Get object
	 * 
	 * @param name
	 * @return Object 一An instance of a bean registered with the given name
	 * @throws org.springframework.beans.BeansException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) beanFactory.getBean(name);
	}

	/**
	 * Get an object of type requiredType
	 * 
	 * @param clz
	 * @return
	 * @throws org.springframework.beans.BeansException
	 * 
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException {
		@SuppressWarnings("unchecked")
		T result = (T) beanFactory.getBean(clz);
		return result;
	}

	/**
	 * Return true if the BeanFactory contains a bean definition that matches the given name
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return beanFactory.containsBean(name);
	}

	/**
	 * Determines whether the bean definition registered with the given name is a singleton or a prototype.
	 * If the bean definition corresponding to the given name is not found, an exception will be thrown (NoSuchBeanDefinitionException)
	 * @param name
	 * @return boolean
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 * 
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isSingleton(name);
	}

	/**
	 * @param name
	 * @return Class Type of registered object
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 * 
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getType(name);
	}

	/**
	 * Returns the alias if the given bean name has an alias in the bean definition
	 * 
	 * @param name
	 * @return
	 * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
	 * 
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getAliases(name);
	}

}
