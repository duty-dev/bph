package com.tianyi.bph.query.alarm;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Jjdb110Example {
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;

	public Jjdb110Example() {
		oredCriteria = new ArrayList<Criteria>();
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		protected void addCriterionForJDBCDate(String condition, Date value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value.getTime()),
					property);
		}

		protected void addCriterionForJDBCDate(String condition,
				List<Date> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
			Iterator<Date> iter = values.iterator();
			while (iter.hasNext()) {
				dateList.add(new java.sql.Date(iter.next().getTime()));
			}
			addCriterion(condition, dateList, property);
		}

		protected void addCriterionForJDBCDate(String condition, Date value1,
				Date value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			addCriterion(condition, new java.sql.Date(value1.getTime()),
					new java.sql.Date(value2.getTime()), property);
		}

		public Criteria andJjdbhIsNull() {
			addCriterion("JJDBH is null");
			return (Criteria) this;
		}

		public Criteria andJjdbhIsNotNull() {
			addCriterion("JJDBH is not null");
			return (Criteria) this;
		}

		public Criteria andJjdbhEqualTo(String value) {
			addCriterion("JJDBH =", value, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andJjdbhNotEqualTo(String value) {
			addCriterion("JJDBH <>", value, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andJjdbhGreaterThan(String value) {
			addCriterion("JJDBH >", value, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andJjdbhGreaterThanOrEqualTo(String value) {
			addCriterion("JJDBH >=", value, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andJjdbhLessThan(String value) {
			addCriterion("JJDBH <", value, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andJjdbhLessThanOrEqualTo(String value) {
			addCriterion("JJDBH <=", value, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andJjdbhLike(String value) {
			addCriterion("JJDBH like", value, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andJjdbhNotLike(String value) {
			addCriterion("JJDBH not like", value, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andJjdbhIn(List<String> values) {
			addCriterion("JJDBH in", values, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andJjdbhNotIn(List<String> values) {
			addCriterion("JJDBH not in", values, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andJjdbhBetween(String value1, String value2) {
			addCriterion("JJDBH between", value1, value2, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andJjdbhNotBetween(String value1, String value2) {
			addCriterion("JJDBH not between", value1, value2, "jjdbh");
			return (Criteria) this;
		}

		public Criteria andAjztIsNull() {
			addCriterion("AJZT is null");
			return (Criteria) this;
		}

		public Criteria andAjztIsNotNull() {
			addCriterion("AJZT is not null");
			return (Criteria) this;
		}

		public Criteria andAjztEqualTo(Integer value) {
			addCriterion("AJZT =", value, "ajzt");
			return (Criteria) this;
		}

		public Criteria andAjztNotEqualTo(Integer value) {
			addCriterion("AJZT <>", value, "ajzt");
			return (Criteria) this;
		}

		public Criteria andAjztGreaterThan(Integer value) {
			addCriterion("AJZT >", value, "ajzt");
			return (Criteria) this;
		}

		public Criteria andAjztGreaterThanOrEqualTo(Integer value) {
			addCriterion("AJZT >=", value, "ajzt");
			return (Criteria) this;
		}

		public Criteria andAjztLessThan(Integer value) {
			addCriterion("AJZT <", value, "ajzt");
			return (Criteria) this;
		}

		public Criteria andAjztLessThanOrEqualTo(Integer value) {
			addCriterion("AJZT <=", value, "ajzt");
			return (Criteria) this;
		}

		public Criteria andAjztIn(List<Integer> values) {
			addCriterion("AJZT in", values, "ajzt");
			return (Criteria) this;
		}

		public Criteria andAjztNotIn(List<Integer> values) {
			addCriterion("AJZT not in", values, "ajzt");
			return (Criteria) this;
		}

		public Criteria andAjztBetween(Integer value1, Integer value2) {
			addCriterion("AJZT between", value1, value2, "ajzt");
			return (Criteria) this;
		}

		public Criteria andAjztNotBetween(Integer value1, Integer value2) {
			addCriterion("AJZT not between", value1, value2, "ajzt");
			return (Criteria) this;
		}

		public Criteria andBjdhIsNull() {
			addCriterion("BJDH is null");
			return (Criteria) this;
		}

		public Criteria andBjdhIsNotNull() {
			addCriterion("BJDH is not null");
			return (Criteria) this;
		}

		public Criteria andBjdhEqualTo(String value) {
			addCriterion("BJDH =", value, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhNotEqualTo(String value) {
			addCriterion("BJDH <>", value, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhGreaterThan(String value) {
			addCriterion("BJDH >", value, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhGreaterThanOrEqualTo(String value) {
			addCriterion("BJDH >=", value, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhLessThan(String value) {
			addCriterion("BJDH <", value, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhLessThanOrEqualTo(String value) {
			addCriterion("BJDH <=", value, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhLike(String value) {
			addCriterion("BJDH like", value, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhNotLike(String value) {
			addCriterion("BJDH not like", value, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhIn(List<String> values) {
			addCriterion("BJDH in", values, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhNotIn(List<String> values) {
			addCriterion("BJDH not in", values, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhBetween(String value1, String value2) {
			addCriterion("BJDH between", value1, value2, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhNotBetween(String value1, String value2) {
			addCriterion("BJDH not between", value1, value2, "bjdh");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzIsNull() {
			addCriterion("BJDHYHDZ is null");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzIsNotNull() {
			addCriterion("BJDHYHDZ is not null");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzEqualTo(String value) {
			addCriterion("BJDHYHDZ =", value, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzNotEqualTo(String value) {
			addCriterion("BJDHYHDZ <>", value, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzGreaterThan(String value) {
			addCriterion("BJDHYHDZ >", value, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzGreaterThanOrEqualTo(String value) {
			addCriterion("BJDHYHDZ >=", value, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzLessThan(String value) {
			addCriterion("BJDHYHDZ <", value, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzLessThanOrEqualTo(String value) {
			addCriterion("BJDHYHDZ <=", value, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzLike(String value) {
			addCriterion("BJDHYHDZ like", value, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzNotLike(String value) {
			addCriterion("BJDHYHDZ not like", value, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzIn(List<String> values) {
			addCriterion("BJDHYHDZ in", values, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzNotIn(List<String> values) {
			addCriterion("BJDHYHDZ not in", values, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzBetween(String value1, String value2) {
			addCriterion("BJDHYHDZ between", value1, value2, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhdzNotBetween(String value1, String value2) {
			addCriterion("BJDHYHDZ not between", value1, value2, "bjdhyhdz");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmIsNull() {
			addCriterion("BJDHYHXM is null");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmIsNotNull() {
			addCriterion("BJDHYHXM is not null");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmEqualTo(String value) {
			addCriterion("BJDHYHXM =", value, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmNotEqualTo(String value) {
			addCriterion("BJDHYHXM <>", value, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmGreaterThan(String value) {
			addCriterion("BJDHYHXM >", value, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmGreaterThanOrEqualTo(String value) {
			addCriterion("BJDHYHXM >=", value, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmLessThan(String value) {
			addCriterion("BJDHYHXM <", value, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmLessThanOrEqualTo(String value) {
			addCriterion("BJDHYHXM <=", value, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmLike(String value) {
			addCriterion("BJDHYHXM like", value, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmNotLike(String value) {
			addCriterion("BJDHYHXM not like", value, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmIn(List<String> values) {
			addCriterion("BJDHYHXM in", values, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmNotIn(List<String> values) {
			addCriterion("BJDHYHXM not in", values, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmBetween(String value1, String value2) {
			addCriterion("BJDHYHXM between", value1, value2, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjdhyhxmNotBetween(String value1, String value2) {
			addCriterion("BJDHYHXM not between", value1, value2, "bjdhyhxm");
			return (Criteria) this;
		}

		public Criteria andBjfsbhIsNull() {
			addCriterion("BJFSBH is null");
			return (Criteria) this;
		}

		public Criteria andBjfsbhIsNotNull() {
			addCriterion("BJFSBH is not null");
			return (Criteria) this;
		}

		public Criteria andBjfsbhEqualTo(String value) {
			addCriterion("BJFSBH =", value, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andBjfsbhNotEqualTo(String value) {
			addCriterion("BJFSBH <>", value, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andBjfsbhGreaterThan(String value) {
			addCriterion("BJFSBH >", value, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andBjfsbhGreaterThanOrEqualTo(String value) {
			addCriterion("BJFSBH >=", value, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andBjfsbhLessThan(String value) {
			addCriterion("BJFSBH <", value, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andBjfsbhLessThanOrEqualTo(String value) {
			addCriterion("BJFSBH <=", value, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andBjfsbhLike(String value) {
			addCriterion("BJFSBH like", value, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andBjfsbhNotLike(String value) {
			addCriterion("BJFSBH not like", value, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andBjfsbhIn(List<String> values) {
			addCriterion("BJFSBH in", values, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andBjfsbhNotIn(List<String> values) {
			addCriterion("BJFSBH not in", values, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andBjfsbhBetween(String value1, String value2) {
			addCriterion("BJFSBH between", value1, value2, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andBjfsbhNotBetween(String value1, String value2) {
			addCriterion("BJFSBH not between", value1, value2, "bjfsbh");
			return (Criteria) this;
		}

		public Criteria andCaseLevelIsNull() {
			addCriterion("CASE_LEVEL is null");
			return (Criteria) this;
		}

		public Criteria andCaseLevelIsNotNull() {
			addCriterion("CASE_LEVEL is not null");
			return (Criteria) this;
		}

		public Criteria andCaseLevelEqualTo(String value) {
			addCriterion("CASE_LEVEL =", value, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andCaseLevelNotEqualTo(String value) {
			addCriterion("CASE_LEVEL <>", value, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andCaseLevelGreaterThan(String value) {
			addCriterion("CASE_LEVEL >", value, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andCaseLevelGreaterThanOrEqualTo(String value) {
			addCriterion("CASE_LEVEL >=", value, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andCaseLevelLessThan(String value) {
			addCriterion("CASE_LEVEL <", value, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andCaseLevelLessThanOrEqualTo(String value) {
			addCriterion("CASE_LEVEL <=", value, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andCaseLevelLike(String value) {
			addCriterion("CASE_LEVEL like", value, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andCaseLevelNotLike(String value) {
			addCriterion("CASE_LEVEL not like", value, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andCaseLevelIn(List<String> values) {
			addCriterion("CASE_LEVEL in", values, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andCaseLevelNotIn(List<String> values) {
			addCriterion("CASE_LEVEL not in", values, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andCaseLevelBetween(String value1, String value2) {
			addCriterion("CASE_LEVEL between", value1, value2, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andCaseLevelNotBetween(String value1, String value2) {
			addCriterion("CASE_LEVEL not between", value1, value2, "caseLevel");
			return (Criteria) this;
		}

		public Criteria andBjlbIsNull() {
			addCriterion("BJLB is null");
			return (Criteria) this;
		}

		public Criteria andBjlbIsNotNull() {
			addCriterion("BJLB is not null");
			return (Criteria) this;
		}

		public Criteria andBjlbEqualTo(String value) {
			addCriterion("BJLB =", value, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlbNotEqualTo(String value) {
			addCriterion("BJLB <>", value, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlbGreaterThan(String value) {
			addCriterion("BJLB >", value, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlbGreaterThanOrEqualTo(String value) {
			addCriterion("BJLB >=", value, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlbLessThan(String value) {
			addCriterion("BJLB <", value, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlbLessThanOrEqualTo(String value) {
			addCriterion("BJLB <=", value, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlbLike(String value) {
			addCriterion("BJLB like", value, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlbNotLike(String value) {
			addCriterion("BJLB not like", value, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlbIn(List<String> values) {
			addCriterion("BJLB in", values, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlbNotIn(List<String> values) {
			addCriterion("BJLB not in", values, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlbBetween(String value1, String value2) {
			addCriterion("BJLB between", value1, value2, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlbNotBetween(String value1, String value2) {
			addCriterion("BJLB not between", value1, value2, "bjlb");
			return (Criteria) this;
		}

		public Criteria andBjlxIsNull() {
			addCriterion("BJLX is null");
			return (Criteria) this;
		}

		public Criteria andBjlxIsNotNull() {
			addCriterion("BJLX is not null");
			return (Criteria) this;
		}

		public Criteria andBjlxEqualTo(String value) {
			addCriterion("BJLX =", value, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjlxNotEqualTo(String value) {
			addCriterion("BJLX <>", value, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjlxGreaterThan(String value) {
			addCriterion("BJLX >", value, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjlxGreaterThanOrEqualTo(String value) {
			addCriterion("BJLX >=", value, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjlxLessThan(String value) {
			addCriterion("BJLX <", value, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjlxLessThanOrEqualTo(String value) {
			addCriterion("BJLX <=", value, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjlxLike(String value) {
			addCriterion("BJLX like", value, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjlxNotLike(String value) {
			addCriterion("BJLX not like", value, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjlxIn(List<String> values) {
			addCriterion("BJLX in", values, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjlxNotIn(List<String> values) {
			addCriterion("BJLX not in", values, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjlxBetween(String value1, String value2) {
			addCriterion("BJLX between", value1, value2, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjlxNotBetween(String value1, String value2) {
			addCriterion("BJLX not between", value1, value2, "bjlx");
			return (Criteria) this;
		}

		public Criteria andBjxlIsNull() {
			addCriterion("BJXL is null");
			return (Criteria) this;
		}

		public Criteria andBjxlIsNotNull() {
			addCriterion("BJXL is not null");
			return (Criteria) this;
		}

		public Criteria andBjxlEqualTo(String value) {
			addCriterion("BJXL =", value, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjxlNotEqualTo(String value) {
			addCriterion("BJXL <>", value, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjxlGreaterThan(String value) {
			addCriterion("BJXL >", value, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjxlGreaterThanOrEqualTo(String value) {
			addCriterion("BJXL >=", value, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjxlLessThan(String value) {
			addCriterion("BJXL <", value, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjxlLessThanOrEqualTo(String value) {
			addCriterion("BJXL <=", value, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjxlLike(String value) {
			addCriterion("BJXL like", value, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjxlNotLike(String value) {
			addCriterion("BJXL not like", value, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjxlIn(List<String> values) {
			addCriterion("BJXL in", values, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjxlNotIn(List<String> values) {
			addCriterion("BJXL not in", values, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjxlBetween(String value1, String value2) {
			addCriterion("BJXL between", value1, value2, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjxlNotBetween(String value1, String value2) {
			addCriterion("BJXL not between", value1, value2, "bjxl");
			return (Criteria) this;
		}

		public Criteria andBjnrIsNull() {
			addCriterion("BJNR is null");
			return (Criteria) this;
		}

		public Criteria andBjnrIsNotNull() {
			addCriterion("BJNR is not null");
			return (Criteria) this;
		}

		public Criteria andBjnrEqualTo(String value) {
			addCriterion("BJNR =", value, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBjnrNotEqualTo(String value) {
			addCriterion("BJNR <>", value, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBjnrGreaterThan(String value) {
			addCriterion("BJNR >", value, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBjnrGreaterThanOrEqualTo(String value) {
			addCriterion("BJNR >=", value, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBjnrLessThan(String value) {
			addCriterion("BJNR <", value, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBjnrLessThanOrEqualTo(String value) {
			addCriterion("BJNR <=", value, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBjnrLike(String value) {
			addCriterion("BJNR like", value, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBjnrNotLike(String value) {
			addCriterion("BJNR not like", value, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBjnrIn(List<String> values) {
			addCriterion("BJNR in", values, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBjnrNotIn(List<String> values) {
			addCriterion("BJNR not in", values, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBjnrBetween(String value1, String value2) {
			addCriterion("BJNR between", value1, value2, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBjnrNotBetween(String value1, String value2) {
			addCriterion("BJNR not between", value1, value2, "bjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrIsNull() {
			addCriterion("BCJJNR is null");
			return (Criteria) this;
		}

		public Criteria andBcjjnrIsNotNull() {
			addCriterion("BCJJNR is not null");
			return (Criteria) this;
		}

		public Criteria andBcjjnrEqualTo(String value) {
			addCriterion("BCJJNR =", value, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrNotEqualTo(String value) {
			addCriterion("BCJJNR <>", value, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrGreaterThan(String value) {
			addCriterion("BCJJNR >", value, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrGreaterThanOrEqualTo(String value) {
			addCriterion("BCJJNR >=", value, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrLessThan(String value) {
			addCriterion("BCJJNR <", value, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrLessThanOrEqualTo(String value) {
			addCriterion("BCJJNR <=", value, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrLike(String value) {
			addCriterion("BCJJNR like", value, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrNotLike(String value) {
			addCriterion("BCJJNR not like", value, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrIn(List<String> values) {
			addCriterion("BCJJNR in", values, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrNotIn(List<String> values) {
			addCriterion("BCJJNR not in", values, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrBetween(String value1, String value2) {
			addCriterion("BCJJNR between", value1, value2, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBcjjnrNotBetween(String value1, String value2) {
			addCriterion("BCJJNR not between", value1, value2, "bcjjnr");
			return (Criteria) this;
		}

		public Criteria andBjrxmIsNull() {
			addCriterion("BJRXM is null");
			return (Criteria) this;
		}

		public Criteria andBjrxmIsNotNull() {
			addCriterion("BJRXM is not null");
			return (Criteria) this;
		}

		public Criteria andBjrxmEqualTo(String value) {
			addCriterion("BJRXM =", value, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxmNotEqualTo(String value) {
			addCriterion("BJRXM <>", value, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxmGreaterThan(String value) {
			addCriterion("BJRXM >", value, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxmGreaterThanOrEqualTo(String value) {
			addCriterion("BJRXM >=", value, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxmLessThan(String value) {
			addCriterion("BJRXM <", value, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxmLessThanOrEqualTo(String value) {
			addCriterion("BJRXM <=", value, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxmLike(String value) {
			addCriterion("BJRXM like", value, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxmNotLike(String value) {
			addCriterion("BJRXM not like", value, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxmIn(List<String> values) {
			addCriterion("BJRXM in", values, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxmNotIn(List<String> values) {
			addCriterion("BJRXM not in", values, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxmBetween(String value1, String value2) {
			addCriterion("BJRXM between", value1, value2, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxmNotBetween(String value1, String value2) {
			addCriterion("BJRXM not between", value1, value2, "bjrxm");
			return (Criteria) this;
		}

		public Criteria andBjrxbIsNull() {
			addCriterion("BJRXB is null");
			return (Criteria) this;
		}

		public Criteria andBjrxbIsNotNull() {
			addCriterion("BJRXB is not null");
			return (Criteria) this;
		}

		public Criteria andBjrxbEqualTo(String value) {
			addCriterion("BJRXB =", value, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjrxbNotEqualTo(String value) {
			addCriterion("BJRXB <>", value, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjrxbGreaterThan(String value) {
			addCriterion("BJRXB >", value, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjrxbGreaterThanOrEqualTo(String value) {
			addCriterion("BJRXB >=", value, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjrxbLessThan(String value) {
			addCriterion("BJRXB <", value, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjrxbLessThanOrEqualTo(String value) {
			addCriterion("BJRXB <=", value, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjrxbLike(String value) {
			addCriterion("BJRXB like", value, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjrxbNotLike(String value) {
			addCriterion("BJRXB not like", value, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjrxbIn(List<String> values) {
			addCriterion("BJRXB in", values, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjrxbNotIn(List<String> values) {
			addCriterion("BJRXB not in", values, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjrxbBetween(String value1, String value2) {
			addCriterion("BJRXB between", value1, value2, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjrxbNotBetween(String value1, String value2) {
			addCriterion("BJRXB not between", value1, value2, "bjrxb");
			return (Criteria) this;
		}

		public Criteria andBjsjIsNull() {
			addCriterion("BJSJ is null");
			return (Criteria) this;
		}

		public Criteria andBjsjIsNotNull() {
			addCriterion("BJSJ is not null");
			return (Criteria) this;
		}

		public Criteria andBjsjEqualTo(Date value) {
			addCriterionForJDBCDate("BJSJ =", value, "bjsj");
			return (Criteria) this;
		}

		public Criteria andBjsjNotEqualTo(Date value) {
			addCriterionForJDBCDate("BJSJ <>", value, "bjsj");
			return (Criteria) this;
		}

		public Criteria andBjsjGreaterThan(Date value) {
			addCriterionForJDBCDate("BJSJ >", value, "bjsj");
			return (Criteria) this;
		}

		public Criteria andBjsjGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("BJSJ >=", value, "bjsj");
			return (Criteria) this;
		}

		public Criteria andBjsjLessThan(Date value) {
			addCriterionForJDBCDate("BJSJ <", value, "bjsj");
			return (Criteria) this;
		}

		public Criteria andBjsjLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("BJSJ <=", value, "bjsj");
			return (Criteria) this;
		}

		public Criteria andBjsjIn(List<Date> values) {
			addCriterionForJDBCDate("BJSJ in", values, "bjsj");
			return (Criteria) this;
		}

		public Criteria andBjsjNotIn(List<Date> values) {
			addCriterionForJDBCDate("BJSJ not in", values, "bjsj");
			return (Criteria) this;
		}

		public Criteria andBjsjBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("BJSJ between", value1, value2, "bjsj");
			return (Criteria) this;
		}

		public Criteria andBjsjNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("BJSJ not between", value1, value2, "bjsj");
			return (Criteria) this;
		}

		public Criteria andFkyqIsNull() {
			addCriterion("FKYQ is null");
			return (Criteria) this;
		}

		public Criteria andFkyqIsNotNull() {
			addCriterion("FKYQ is not null");
			return (Criteria) this;
		}

		public Criteria andFkyqEqualTo(String value) {
			addCriterion("FKYQ =", value, "fkyq");
			return (Criteria) this;
		}

		public Criteria andFkyqNotEqualTo(String value) {
			addCriterion("FKYQ <>", value, "fkyq");
			return (Criteria) this;
		}

		public Criteria andFkyqGreaterThan(String value) {
			addCriterion("FKYQ >", value, "fkyq");
			return (Criteria) this;
		}

		public Criteria andFkyqGreaterThanOrEqualTo(String value) {
			addCriterion("FKYQ >=", value, "fkyq");
			return (Criteria) this;
		}

		public Criteria andFkyqLessThan(String value) {
			addCriterion("FKYQ <", value, "fkyq");
			return (Criteria) this;
		}

		public Criteria andFkyqLessThanOrEqualTo(String value) {
			addCriterion("FKYQ <=", value, "fkyq");
			return (Criteria) this;
		}

		public Criteria andFkyqLike(String value) {
			addCriterion("FKYQ like", value, "fkyq");
			return (Criteria) this;
		}

		public Criteria andFkyqNotLike(String value) {
			addCriterion("FKYQ not like", value, "fkyq");
			return (Criteria) this;
		}

		public Criteria andFkyqIn(List<String> values) {
			addCriterion("FKYQ in", values, "fkyq");
			return (Criteria) this;
		}

		public Criteria andFkyqNotIn(List<String> values) {
			addCriterion("FKYQ not in", values, "fkyq");
			return (Criteria) this;
		}

		public Criteria andFkyqBetween(String value1, String value2) {
			addCriterion("FKYQ between", value1, value2, "fkyq");
			return (Criteria) this;
		}

		public Criteria andFkyqNotBetween(String value1, String value2) {
			addCriterion("FKYQ not between", value1, value2, "fkyq");
			return (Criteria) this;
		}

		public Criteria andGljjdbhIsNull() {
			addCriterion("GLJJDBH is null");
			return (Criteria) this;
		}

		public Criteria andGljjdbhIsNotNull() {
			addCriterion("GLJJDBH is not null");
			return (Criteria) this;
		}

		public Criteria andGljjdbhEqualTo(String value) {
			addCriterion("GLJJDBH =", value, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGljjdbhNotEqualTo(String value) {
			addCriterion("GLJJDBH <>", value, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGljjdbhGreaterThan(String value) {
			addCriterion("GLJJDBH >", value, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGljjdbhGreaterThanOrEqualTo(String value) {
			addCriterion("GLJJDBH >=", value, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGljjdbhLessThan(String value) {
			addCriterion("GLJJDBH <", value, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGljjdbhLessThanOrEqualTo(String value) {
			addCriterion("GLJJDBH <=", value, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGljjdbhLike(String value) {
			addCriterion("GLJJDBH like", value, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGljjdbhNotLike(String value) {
			addCriterion("GLJJDBH not like", value, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGljjdbhIn(List<String> values) {
			addCriterion("GLJJDBH in", values, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGljjdbhNotIn(List<String> values) {
			addCriterion("GLJJDBH not in", values, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGljjdbhBetween(String value1, String value2) {
			addCriterion("GLJJDBH between", value1, value2, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGljjdbhNotBetween(String value1, String value2) {
			addCriterion("GLJJDBH not between", value1, value2, "gljjdbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhIsNull() {
			addCriterion("GXDWBH is null");
			return (Criteria) this;
		}

		public Criteria andGxdwbhIsNotNull() {
			addCriterion("GXDWBH is not null");
			return (Criteria) this;
		}

		public Criteria andGxdwbhEqualTo(String value) {
			addCriterion("GXDWBH =", value, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhNotEqualTo(String value) {
			addCriterion("GXDWBH <>", value, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhGreaterThan(String value) {
			addCriterion("GXDWBH >", value, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhGreaterThanOrEqualTo(String value) {
			addCriterion("GXDWBH >=", value, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhLessThan(String value) {
			addCriterion("GXDWBH <", value, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhLessThanOrEqualTo(String value) {
			addCriterion("GXDWBH <=", value, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhLike(String value) {
			addCriterion("GXDWBH like", value, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhNotLike(String value) {
			addCriterion("GXDWBH not like", value, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhIn(List<String> values) {
			addCriterion("GXDWBH in", values, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhNotIn(List<String> values) {
			addCriterion("GXDWBH not in", values, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhBetween(String value1, String value2) {
			addCriterion("GXDWBH between", value1, value2, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andGxdwbhNotBetween(String value1, String value2) {
			addCriterion("GXDWBH not between", value1, value2, "gxdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhIsNull() {
			addCriterion("JJDWBH is null");
			return (Criteria) this;
		}

		public Criteria andJjdwbhIsNotNull() {
			addCriterion("JJDWBH is not null");
			return (Criteria) this;
		}

		public Criteria andJjdwbhEqualTo(String value) {
			addCriterion("JJDWBH =", value, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhNotEqualTo(String value) {
			addCriterion("JJDWBH <>", value, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhGreaterThan(String value) {
			addCriterion("JJDWBH >", value, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhGreaterThanOrEqualTo(String value) {
			addCriterion("JJDWBH >=", value, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhLessThan(String value) {
			addCriterion("JJDWBH <", value, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhLessThanOrEqualTo(String value) {
			addCriterion("JJDWBH <=", value, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhLike(String value) {
			addCriterion("JJDWBH like", value, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhNotLike(String value) {
			addCriterion("JJDWBH not like", value, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhIn(List<String> values) {
			addCriterion("JJDWBH in", values, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhNotIn(List<String> values) {
			addCriterion("JJDWBH not in", values, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhBetween(String value1, String value2) {
			addCriterion("JJDWBH between", value1, value2, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjdwbhNotBetween(String value1, String value2) {
			addCriterion("JJDWBH not between", value1, value2, "jjdwbh");
			return (Criteria) this;
		}

		public Criteria andJjsjIsNull() {
			addCriterion("JJSJ is null");
			return (Criteria) this;
		}

		public Criteria andJjsjIsNotNull() {
			addCriterion("JJSJ is not null");
			return (Criteria) this;
		}

		public Criteria andJjsjEqualTo(Date value) {
			addCriterionForJDBCDate("JJSJ =", value, "jjsj");
			return (Criteria) this;
		}

		public Criteria andJjsjNotEqualTo(Date value) {
			addCriterionForJDBCDate("JJSJ <>", value, "jjsj");
			return (Criteria) this;
		}

		public Criteria andJjsjGreaterThan(Date value) {
			addCriterionForJDBCDate("JJSJ >", value, "jjsj");
			return (Criteria) this;
		}

		public Criteria andJjsjGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("JJSJ >=", value, "jjsj");
			return (Criteria) this;
		}

		public Criteria andJjsjLessThan(Date value) {
			addCriterionForJDBCDate("JJSJ <", value, "jjsj");
			return (Criteria) this;
		}

		public Criteria andJjsjLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("JJSJ <=", value, "jjsj");
			return (Criteria) this;
		}

		public Criteria andJjsjIn(List<Date> values) {
			addCriterionForJDBCDate("JJSJ in", values, "jjsj");
			return (Criteria) this;
		}

		public Criteria andJjsjNotIn(List<Date> values) {
			addCriterionForJDBCDate("JJSJ not in", values, "jjsj");
			return (Criteria) this;
		}

		public Criteria andJjsjBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("JJSJ between", value1, value2, "jjsj");
			return (Criteria) this;
		}

		public Criteria andJjsjNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("JJSJ not between", value1, value2, "jjsj");
			return (Criteria) this;
		}

		public Criteria andJjybhIsNull() {
			addCriterion("JJYBH is null");
			return (Criteria) this;
		}

		public Criteria andJjybhIsNotNull() {
			addCriterion("JJYBH is not null");
			return (Criteria) this;
		}

		public Criteria andJjybhEqualTo(String value) {
			addCriterion("JJYBH =", value, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjybhNotEqualTo(String value) {
			addCriterion("JJYBH <>", value, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjybhGreaterThan(String value) {
			addCriterion("JJYBH >", value, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjybhGreaterThanOrEqualTo(String value) {
			addCriterion("JJYBH >=", value, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjybhLessThan(String value) {
			addCriterion("JJYBH <", value, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjybhLessThanOrEqualTo(String value) {
			addCriterion("JJYBH <=", value, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjybhLike(String value) {
			addCriterion("JJYBH like", value, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjybhNotLike(String value) {
			addCriterion("JJYBH not like", value, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjybhIn(List<String> values) {
			addCriterion("JJYBH in", values, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjybhNotIn(List<String> values) {
			addCriterion("JJYBH not in", values, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjybhBetween(String value1, String value2) {
			addCriterion("JJYBH between", value1, value2, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjybhNotBetween(String value1, String value2) {
			addCriterion("JJYBH not between", value1, value2, "jjybh");
			return (Criteria) this;
		}

		public Criteria andJjyxmIsNull() {
			addCriterion("JJYXM is null");
			return (Criteria) this;
		}

		public Criteria andJjyxmIsNotNull() {
			addCriterion("JJYXM is not null");
			return (Criteria) this;
		}

		public Criteria andJjyxmEqualTo(String value) {
			addCriterion("JJYXM =", value, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andJjyxmNotEqualTo(String value) {
			addCriterion("JJYXM <>", value, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andJjyxmGreaterThan(String value) {
			addCriterion("JJYXM >", value, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andJjyxmGreaterThanOrEqualTo(String value) {
			addCriterion("JJYXM >=", value, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andJjyxmLessThan(String value) {
			addCriterion("JJYXM <", value, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andJjyxmLessThanOrEqualTo(String value) {
			addCriterion("JJYXM <=", value, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andJjyxmLike(String value) {
			addCriterion("JJYXM like", value, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andJjyxmNotLike(String value) {
			addCriterion("JJYXM not like", value, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andJjyxmIn(List<String> values) {
			addCriterion("JJYXM in", values, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andJjyxmNotIn(List<String> values) {
			addCriterion("JJYXM not in", values, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andJjyxmBetween(String value1, String value2) {
			addCriterion("JJYXM between", value1, value2, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andJjyxmNotBetween(String value1, String value2) {
			addCriterion("JJYXM not between", value1, value2, "jjyxm");
			return (Criteria) this;
		}

		public Criteria andLdgbhIsNull() {
			addCriterion("LDGBH is null");
			return (Criteria) this;
		}

		public Criteria andLdgbhIsNotNull() {
			addCriterion("LDGBH is not null");
			return (Criteria) this;
		}

		public Criteria andLdgbhEqualTo(String value) {
			addCriterion("LDGBH =", value, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLdgbhNotEqualTo(String value) {
			addCriterion("LDGBH <>", value, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLdgbhGreaterThan(String value) {
			addCriterion("LDGBH >", value, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLdgbhGreaterThanOrEqualTo(String value) {
			addCriterion("LDGBH >=", value, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLdgbhLessThan(String value) {
			addCriterion("LDGBH <", value, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLdgbhLessThanOrEqualTo(String value) {
			addCriterion("LDGBH <=", value, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLdgbhLike(String value) {
			addCriterion("LDGBH like", value, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLdgbhNotLike(String value) {
			addCriterion("LDGBH not like", value, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLdgbhIn(List<String> values) {
			addCriterion("LDGBH in", values, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLdgbhNotIn(List<String> values) {
			addCriterion("LDGBH not in", values, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLdgbhBetween(String value1, String value2) {
			addCriterion("LDGBH between", value1, value2, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLdgbhNotBetween(String value1, String value2) {
			addCriterion("LDGBH not between", value1, value2, "ldgbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhIsNull() {
			addCriterion("LHLXBH is null");
			return (Criteria) this;
		}

		public Criteria andLhlxbhIsNotNull() {
			addCriterion("LHLXBH is not null");
			return (Criteria) this;
		}

		public Criteria andLhlxbhEqualTo(String value) {
			addCriterion("LHLXBH =", value, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhNotEqualTo(String value) {
			addCriterion("LHLXBH <>", value, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhGreaterThan(String value) {
			addCriterion("LHLXBH >", value, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhGreaterThanOrEqualTo(String value) {
			addCriterion("LHLXBH >=", value, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhLessThan(String value) {
			addCriterion("LHLXBH <", value, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhLessThanOrEqualTo(String value) {
			addCriterion("LHLXBH <=", value, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhLike(String value) {
			addCriterion("LHLXBH like", value, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhNotLike(String value) {
			addCriterion("LHLXBH not like", value, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhIn(List<String> values) {
			addCriterion("LHLXBH in", values, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhNotIn(List<String> values) {
			addCriterion("LHLXBH not in", values, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhBetween(String value1, String value2) {
			addCriterion("LHLXBH between", value1, value2, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLhlxbhNotBetween(String value1, String value2) {
			addCriterion("LHLXBH not between", value1, value2, "lhlxbh");
			return (Criteria) this;
		}

		public Criteria andLxdhIsNull() {
			addCriterion("LXDH is null");
			return (Criteria) this;
		}

		public Criteria andLxdhIsNotNull() {
			addCriterion("LXDH is not null");
			return (Criteria) this;
		}

		public Criteria andLxdhEqualTo(String value) {
			addCriterion("LXDH =", value, "lxdh");
			return (Criteria) this;
		}

		public Criteria andLxdhNotEqualTo(String value) {
			addCriterion("LXDH <>", value, "lxdh");
			return (Criteria) this;
		}

		public Criteria andLxdhGreaterThan(String value) {
			addCriterion("LXDH >", value, "lxdh");
			return (Criteria) this;
		}

		public Criteria andLxdhGreaterThanOrEqualTo(String value) {
			addCriterion("LXDH >=", value, "lxdh");
			return (Criteria) this;
		}

		public Criteria andLxdhLessThan(String value) {
			addCriterion("LXDH <", value, "lxdh");
			return (Criteria) this;
		}

		public Criteria andLxdhLessThanOrEqualTo(String value) {
			addCriterion("LXDH <=", value, "lxdh");
			return (Criteria) this;
		}

		public Criteria andLxdhLike(String value) {
			addCriterion("LXDH like", value, "lxdh");
			return (Criteria) this;
		}

		public Criteria andLxdhNotLike(String value) {
			addCriterion("LXDH not like", value, "lxdh");
			return (Criteria) this;
		}

		public Criteria andLxdhIn(List<String> values) {
			addCriterion("LXDH in", values, "lxdh");
			return (Criteria) this;
		}

		public Criteria andLxdhNotIn(List<String> values) {
			addCriterion("LXDH not in", values, "lxdh");
			return (Criteria) this;
		}

		public Criteria andLxdhBetween(String value1, String value2) {
			addCriterion("LXDH between", value1, value2, "lxdh");
			return (Criteria) this;
		}

		public Criteria andLxdhNotBetween(String value1, String value2) {
			addCriterion("LXDH not between", value1, value2, "lxdh");
			return (Criteria) this;
		}

		public Criteria andSddwxzbIsNull() {
			addCriterion("SDDWXZB is null");
			return (Criteria) this;
		}

		public Criteria andSddwxzbIsNotNull() {
			addCriterion("SDDWXZB is not null");
			return (Criteria) this;
		}

		public Criteria andSddwxzbEqualTo(String value) {
			addCriterion("SDDWXZB =", value, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwxzbNotEqualTo(String value) {
			addCriterion("SDDWXZB <>", value, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwxzbGreaterThan(String value) {
			addCriterion("SDDWXZB >", value, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwxzbGreaterThanOrEqualTo(String value) {
			addCriterion("SDDWXZB >=", value, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwxzbLessThan(String value) {
			addCriterion("SDDWXZB <", value, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwxzbLessThanOrEqualTo(String value) {
			addCriterion("SDDWXZB <=", value, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwxzbLike(String value) {
			addCriterion("SDDWXZB like", value, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwxzbNotLike(String value) {
			addCriterion("SDDWXZB not like", value, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwxzbIn(List<String> values) {
			addCriterion("SDDWXZB in", values, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwxzbNotIn(List<String> values) {
			addCriterion("SDDWXZB not in", values, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwxzbBetween(String value1, String value2) {
			addCriterion("SDDWXZB between", value1, value2, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwxzbNotBetween(String value1, String value2) {
			addCriterion("SDDWXZB not between", value1, value2, "sddwxzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbIsNull() {
			addCriterion("SDDWYZB is null");
			return (Criteria) this;
		}

		public Criteria andSddwyzbIsNotNull() {
			addCriterion("SDDWYZB is not null");
			return (Criteria) this;
		}

		public Criteria andSddwyzbEqualTo(String value) {
			addCriterion("SDDWYZB =", value, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbNotEqualTo(String value) {
			addCriterion("SDDWYZB <>", value, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbGreaterThan(String value) {
			addCriterion("SDDWYZB >", value, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbGreaterThanOrEqualTo(String value) {
			addCriterion("SDDWYZB >=", value, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbLessThan(String value) {
			addCriterion("SDDWYZB <", value, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbLessThanOrEqualTo(String value) {
			addCriterion("SDDWYZB <=", value, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbLike(String value) {
			addCriterion("SDDWYZB like", value, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbNotLike(String value) {
			addCriterion("SDDWYZB not like", value, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbIn(List<String> values) {
			addCriterion("SDDWYZB in", values, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbNotIn(List<String> values) {
			addCriterion("SDDWYZB not in", values, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbBetween(String value1, String value2) {
			addCriterion("SDDWYZB between", value1, value2, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSddwyzbNotBetween(String value1, String value2) {
			addCriterion("SDDWYZB not between", value1, value2, "sddwyzb");
			return (Criteria) this;
		}

		public Criteria andSfdzIsNull() {
			addCriterion("SFDZ is null");
			return (Criteria) this;
		}

		public Criteria andSfdzIsNotNull() {
			addCriterion("SFDZ is not null");
			return (Criteria) this;
		}

		public Criteria andSfdzEqualTo(String value) {
			addCriterion("SFDZ =", value, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfdzNotEqualTo(String value) {
			addCriterion("SFDZ <>", value, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfdzGreaterThan(String value) {
			addCriterion("SFDZ >", value, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfdzGreaterThanOrEqualTo(String value) {
			addCriterion("SFDZ >=", value, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfdzLessThan(String value) {
			addCriterion("SFDZ <", value, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfdzLessThanOrEqualTo(String value) {
			addCriterion("SFDZ <=", value, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfdzLike(String value) {
			addCriterion("SFDZ like", value, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfdzNotLike(String value) {
			addCriterion("SFDZ not like", value, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfdzIn(List<String> values) {
			addCriterion("SFDZ in", values, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfdzNotIn(List<String> values) {
			addCriterion("SFDZ not in", values, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfdzBetween(String value1, String value2) {
			addCriterion("SFDZ between", value1, value2, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfdzNotBetween(String value1, String value2) {
			addCriterion("SFDZ not between", value1, value2, "sfdz");
			return (Criteria) this;
		}

		public Criteria andSfswIsNull() {
			addCriterion("SFSW is null");
			return (Criteria) this;
		}

		public Criteria andSfswIsNotNull() {
			addCriterion("SFSW is not null");
			return (Criteria) this;
		}

		public Criteria andSfswEqualTo(Integer value) {
			addCriterion("SFSW =", value, "sfsw");
			return (Criteria) this;
		}

		public Criteria andSfswNotEqualTo(Integer value) {
			addCriterion("SFSW <>", value, "sfsw");
			return (Criteria) this;
		}

		public Criteria andSfswGreaterThan(Integer value) {
			addCriterion("SFSW >", value, "sfsw");
			return (Criteria) this;
		}

		public Criteria andSfswGreaterThanOrEqualTo(Integer value) {
			addCriterion("SFSW >=", value, "sfsw");
			return (Criteria) this;
		}

		public Criteria andSfswLessThan(Integer value) {
			addCriterion("SFSW <", value, "sfsw");
			return (Criteria) this;
		}

		public Criteria andSfswLessThanOrEqualTo(Integer value) {
			addCriterion("SFSW <=", value, "sfsw");
			return (Criteria) this;
		}

		public Criteria andSfswIn(List<Integer> values) {
			addCriterion("SFSW in", values, "sfsw");
			return (Criteria) this;
		}

		public Criteria andSfswNotIn(List<Integer> values) {
			addCriterion("SFSW not in", values, "sfsw");
			return (Criteria) this;
		}

		public Criteria andSfswBetween(Integer value1, Integer value2) {
			addCriterion("SFSW between", value1, value2, "sfsw");
			return (Criteria) this;
		}

		public Criteria andSfswNotBetween(Integer value1, Integer value2) {
			addCriterion("SFSW not between", value1, value2, "sfsw");
			return (Criteria) this;
		}

		public Criteria andSfswybjIsNull() {
			addCriterion("SFSWYBJ is null");
			return (Criteria) this;
		}

		public Criteria andSfswybjIsNotNull() {
			addCriterion("SFSWYBJ is not null");
			return (Criteria) this;
		}

		public Criteria andSfswybjEqualTo(Integer value) {
			addCriterion("SFSWYBJ =", value, "sfswybj");
			return (Criteria) this;
		}

		public Criteria andSfswybjNotEqualTo(Integer value) {
			addCriterion("SFSWYBJ <>", value, "sfswybj");
			return (Criteria) this;
		}

		public Criteria andSfswybjGreaterThan(Integer value) {
			addCriterion("SFSWYBJ >", value, "sfswybj");
			return (Criteria) this;
		}

		public Criteria andSfswybjGreaterThanOrEqualTo(Integer value) {
			addCriterion("SFSWYBJ >=", value, "sfswybj");
			return (Criteria) this;
		}

		public Criteria andSfswybjLessThan(Integer value) {
			addCriterion("SFSWYBJ <", value, "sfswybj");
			return (Criteria) this;
		}

		public Criteria andSfswybjLessThanOrEqualTo(Integer value) {
			addCriterion("SFSWYBJ <=", value, "sfswybj");
			return (Criteria) this;
		}

		public Criteria andSfswybjIn(List<Integer> values) {
			addCriterion("SFSWYBJ in", values, "sfswybj");
			return (Criteria) this;
		}

		public Criteria andSfswybjNotIn(List<Integer> values) {
			addCriterion("SFSWYBJ not in", values, "sfswybj");
			return (Criteria) this;
		}

		public Criteria andSfswybjBetween(Integer value1, Integer value2) {
			addCriterion("SFSWYBJ between", value1, value2, "sfswybj");
			return (Criteria) this;
		}

		public Criteria andSfswybjNotBetween(Integer value1, Integer value2) {
			addCriterion("SFSWYBJ not between", value1, value2, "sfswybj");
			return (Criteria) this;
		}

		public Criteria andTfhmIsNull() {
			addCriterion("TFHM is null");
			return (Criteria) this;
		}

		public Criteria andTfhmIsNotNull() {
			addCriterion("TFHM is not null");
			return (Criteria) this;
		}

		public Criteria andTfhmEqualTo(String value) {
			addCriterion("TFHM =", value, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTfhmNotEqualTo(String value) {
			addCriterion("TFHM <>", value, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTfhmGreaterThan(String value) {
			addCriterion("TFHM >", value, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTfhmGreaterThanOrEqualTo(String value) {
			addCriterion("TFHM >=", value, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTfhmLessThan(String value) {
			addCriterion("TFHM <", value, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTfhmLessThanOrEqualTo(String value) {
			addCriterion("TFHM <=", value, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTfhmLike(String value) {
			addCriterion("TFHM like", value, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTfhmNotLike(String value) {
			addCriterion("TFHM not like", value, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTfhmIn(List<String> values) {
			addCriterion("TFHM in", values, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTfhmNotIn(List<String> values) {
			addCriterion("TFHM not in", values, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTfhmBetween(String value1, String value2) {
			addCriterion("TFHM between", value1, value2, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTfhmNotBetween(String value1, String value2) {
			addCriterion("TFHM not between", value1, value2, "tfhm");
			return (Criteria) this;
		}

		public Criteria andTransmitunitIsNull() {
			addCriterion("TRANSMITUNIT is null");
			return (Criteria) this;
		}

		public Criteria andTransmitunitIsNotNull() {
			addCriterion("TRANSMITUNIT is not null");
			return (Criteria) this;
		}

		public Criteria andTransmitunitEqualTo(String value) {
			addCriterion("TRANSMITUNIT =", value, "transmitunit");
			return (Criteria) this;
		}

		public Criteria andTransmitunitNotEqualTo(String value) {
			addCriterion("TRANSMITUNIT <>", value, "transmitunit");
			return (Criteria) this;
		}

		public Criteria andTransmitunitGreaterThan(String value) {
			addCriterion("TRANSMITUNIT >", value, "transmitunit");
			return (Criteria) this;
		}

		public Criteria andTransmitunitGreaterThanOrEqualTo(String value) {
			addCriterion("TRANSMITUNIT >=", value, "transmitunit");
			return (Criteria) this;
		}

		public Criteria andTransmitunitLessThan(String value) {
			addCriterion("TRANSMITUNIT <", value, "transmitunit");
			return (Criteria) this;
		}

		public Criteria andTransmitunitLessThanOrEqualTo(String value) {
			addCriterion("TRANSMITUNIT <=", value, "transmitunit");
			return (Criteria) this;
		}

		public Criteria andTransmitunitLike(String value) {
			addCriterion("TRANSMITUNIT like", value, "transmitunit");
			return (Criteria) this;
		}

		public Criteria andTransmitunitNotLike(String value) {
			addCriterion("TRANSMITUNIT not like", value, "transmitunit");
			return (Criteria) this;
		}

		public Criteria andTransmitunitIn(List<String> values) {
			addCriterion("TRANSMITUNIT in", values, "transmitunit");
			return (Criteria) this;
		}

		public Criteria andTransmitunitNotIn(List<String> values) {
			addCriterion("TRANSMITUNIT not in", values, "transmitunit");
			return (Criteria) this;
		}

		public Criteria andTransmitunitBetween(String value1, String value2) {
			addCriterion("TRANSMITUNIT between", value1, value2, "transmitunit");
			return (Criteria) this;
		}

		public Criteria andTransmitunitNotBetween(String value1, String value2) {
			addCriterion("TRANSMITUNIT not between", value1, value2,
					"transmitunit");
			return (Criteria) this;
		}

		public Criteria andXzqhbhIsNull() {
			addCriterion("XZQHBH is null");
			return (Criteria) this;
		}

		public Criteria andXzqhbhIsNotNull() {
			addCriterion("XZQHBH is not null");
			return (Criteria) this;
		}

		public Criteria andXzqhbhEqualTo(String value) {
			addCriterion("XZQHBH =", value, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andXzqhbhNotEqualTo(String value) {
			addCriterion("XZQHBH <>", value, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andXzqhbhGreaterThan(String value) {
			addCriterion("XZQHBH >", value, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andXzqhbhGreaterThanOrEqualTo(String value) {
			addCriterion("XZQHBH >=", value, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andXzqhbhLessThan(String value) {
			addCriterion("XZQHBH <", value, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andXzqhbhLessThanOrEqualTo(String value) {
			addCriterion("XZQHBH <=", value, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andXzqhbhLike(String value) {
			addCriterion("XZQHBH like", value, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andXzqhbhNotLike(String value) {
			addCriterion("XZQHBH not like", value, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andXzqhbhIn(List<String> values) {
			addCriterion("XZQHBH in", values, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andXzqhbhNotIn(List<String> values) {
			addCriterion("XZQHBH not in", values, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andXzqhbhBetween(String value1, String value2) {
			addCriterion("XZQHBH between", value1, value2, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andXzqhbhNotBetween(String value1, String value2) {
			addCriterion("XZQHBH not between", value1, value2, "xzqhbh");
			return (Criteria) this;
		}

		public Criteria andYwbkryIsNull() {
			addCriterion("YWBKRY is null");
			return (Criteria) this;
		}

		public Criteria andYwbkryIsNotNull() {
			addCriterion("YWBKRY is not null");
			return (Criteria) this;
		}

		public Criteria andYwbkryEqualTo(Integer value) {
			addCriterion("YWBKRY =", value, "ywbkry");
			return (Criteria) this;
		}

		public Criteria andYwbkryNotEqualTo(Integer value) {
			addCriterion("YWBKRY <>", value, "ywbkry");
			return (Criteria) this;
		}

		public Criteria andYwbkryGreaterThan(Integer value) {
			addCriterion("YWBKRY >", value, "ywbkry");
			return (Criteria) this;
		}

		public Criteria andYwbkryGreaterThanOrEqualTo(Integer value) {
			addCriterion("YWBKRY >=", value, "ywbkry");
			return (Criteria) this;
		}

		public Criteria andYwbkryLessThan(Integer value) {
			addCriterion("YWBKRY <", value, "ywbkry");
			return (Criteria) this;
		}

		public Criteria andYwbkryLessThanOrEqualTo(Integer value) {
			addCriterion("YWBKRY <=", value, "ywbkry");
			return (Criteria) this;
		}

		public Criteria andYwbkryIn(List<Integer> values) {
			addCriterion("YWBKRY in", values, "ywbkry");
			return (Criteria) this;
		}

		public Criteria andYwbkryNotIn(List<Integer> values) {
			addCriterion("YWBKRY not in", values, "ywbkry");
			return (Criteria) this;
		}

		public Criteria andYwbkryBetween(Integer value1, Integer value2) {
			addCriterion("YWBKRY between", value1, value2, "ywbkry");
			return (Criteria) this;
		}

		public Criteria andYwbkryNotBetween(Integer value1, Integer value2) {
			addCriterion("YWBKRY not between", value1, value2, "ywbkry");
			return (Criteria) this;
		}

		public Criteria andYwbzxlIsNull() {
			addCriterion("YWBZXL is null");
			return (Criteria) this;
		}

		public Criteria andYwbzxlIsNotNull() {
			addCriterion("YWBZXL is not null");
			return (Criteria) this;
		}

		public Criteria andYwbzxlEqualTo(Integer value) {
			addCriterion("YWBZXL =", value, "ywbzxl");
			return (Criteria) this;
		}

		public Criteria andYwbzxlNotEqualTo(Integer value) {
			addCriterion("YWBZXL <>", value, "ywbzxl");
			return (Criteria) this;
		}

		public Criteria andYwbzxlGreaterThan(Integer value) {
			addCriterion("YWBZXL >", value, "ywbzxl");
			return (Criteria) this;
		}

		public Criteria andYwbzxlGreaterThanOrEqualTo(Integer value) {
			addCriterion("YWBZXL >=", value, "ywbzxl");
			return (Criteria) this;
		}

		public Criteria andYwbzxlLessThan(Integer value) {
			addCriterion("YWBZXL <", value, "ywbzxl");
			return (Criteria) this;
		}

		public Criteria andYwbzxlLessThanOrEqualTo(Integer value) {
			addCriterion("YWBZXL <=", value, "ywbzxl");
			return (Criteria) this;
		}

		public Criteria andYwbzxlIn(List<Integer> values) {
			addCriterion("YWBZXL in", values, "ywbzxl");
			return (Criteria) this;
		}

		public Criteria andYwbzxlNotIn(List<Integer> values) {
			addCriterion("YWBZXL not in", values, "ywbzxl");
			return (Criteria) this;
		}

		public Criteria andYwbzxlBetween(Integer value1, Integer value2) {
			addCriterion("YWBZXL between", value1, value2, "ywbzxl");
			return (Criteria) this;
		}

		public Criteria andYwbzxlNotBetween(Integer value1, Integer value2) {
			addCriterion("YWBZXL not between", value1, value2, "ywbzxl");
			return (Criteria) this;
		}

		public Criteria andYwwxwzIsNull() {
			addCriterion("YWWXWZ is null");
			return (Criteria) this;
		}

		public Criteria andYwwxwzIsNotNull() {
			addCriterion("YWWXWZ is not null");
			return (Criteria) this;
		}

		public Criteria andYwwxwzEqualTo(Integer value) {
			addCriterion("YWWXWZ =", value, "ywwxwz");
			return (Criteria) this;
		}

		public Criteria andYwwxwzNotEqualTo(Integer value) {
			addCriterion("YWWXWZ <>", value, "ywwxwz");
			return (Criteria) this;
		}

		public Criteria andYwwxwzGreaterThan(Integer value) {
			addCriterion("YWWXWZ >", value, "ywwxwz");
			return (Criteria) this;
		}

		public Criteria andYwwxwzGreaterThanOrEqualTo(Integer value) {
			addCriterion("YWWXWZ >=", value, "ywwxwz");
			return (Criteria) this;
		}

		public Criteria andYwwxwzLessThan(Integer value) {
			addCriterion("YWWXWZ <", value, "ywwxwz");
			return (Criteria) this;
		}

		public Criteria andYwwxwzLessThanOrEqualTo(Integer value) {
			addCriterion("YWWXWZ <=", value, "ywwxwz");
			return (Criteria) this;
		}

		public Criteria andYwwxwzIn(List<Integer> values) {
			addCriterion("YWWXWZ in", values, "ywwxwz");
			return (Criteria) this;
		}

		public Criteria andYwwxwzNotIn(List<Integer> values) {
			addCriterion("YWWXWZ not in", values, "ywwxwz");
			return (Criteria) this;
		}

		public Criteria andYwwxwzBetween(Integer value1, Integer value2) {
			addCriterion("YWWXWZ between", value1, value2, "ywwxwz");
			return (Criteria) this;
		}

		public Criteria andYwwxwzNotBetween(Integer value1, Integer value2) {
			addCriterion("YWWXWZ not between", value1, value2, "ywwxwz");
			return (Criteria) this;
		}

		public Criteria andYwxajbsIsNull() {
			addCriterion("YWXAJBS is null");
			return (Criteria) this;
		}

		public Criteria andYwxajbsIsNotNull() {
			addCriterion("YWXAJBS is not null");
			return (Criteria) this;
		}

		public Criteria andYwxajbsEqualTo(Integer value) {
			addCriterion("YWXAJBS =", value, "ywxajbs");
			return (Criteria) this;
		}

		public Criteria andYwxajbsNotEqualTo(Integer value) {
			addCriterion("YWXAJBS <>", value, "ywxajbs");
			return (Criteria) this;
		}

		public Criteria andYwxajbsGreaterThan(Integer value) {
			addCriterion("YWXAJBS >", value, "ywxajbs");
			return (Criteria) this;
		}

		public Criteria andYwxajbsGreaterThanOrEqualTo(Integer value) {
			addCriterion("YWXAJBS >=", value, "ywxajbs");
			return (Criteria) this;
		}

		public Criteria andYwxajbsLessThan(Integer value) {
			addCriterion("YWXAJBS <", value, "ywxajbs");
			return (Criteria) this;
		}

		public Criteria andYwxajbsLessThanOrEqualTo(Integer value) {
			addCriterion("YWXAJBS <=", value, "ywxajbs");
			return (Criteria) this;
		}

		public Criteria andYwxajbsIn(List<Integer> values) {
			addCriterion("YWXAJBS in", values, "ywxajbs");
			return (Criteria) this;
		}

		public Criteria andYwxajbsNotIn(List<Integer> values) {
			addCriterion("YWXAJBS not in", values, "ywxajbs");
			return (Criteria) this;
		}

		public Criteria andYwxajbsBetween(Integer value1, Integer value2) {
			addCriterion("YWXAJBS between", value1, value2, "ywxajbs");
			return (Criteria) this;
		}

		public Criteria andYwxajbsNotBetween(Integer value1, Integer value2) {
			addCriterion("YWXAJBS not between", value1, value2, "ywxajbs");
			return (Criteria) this;
		}

		public Criteria andZagjIsNull() {
			addCriterion("ZAGJ is null");
			return (Criteria) this;
		}

		public Criteria andZagjIsNotNull() {
			addCriterion("ZAGJ is not null");
			return (Criteria) this;
		}

		public Criteria andZagjEqualTo(String value) {
			addCriterion("ZAGJ =", value, "zagj");
			return (Criteria) this;
		}

		public Criteria andZagjNotEqualTo(String value) {
			addCriterion("ZAGJ <>", value, "zagj");
			return (Criteria) this;
		}

		public Criteria andZagjGreaterThan(String value) {
			addCriterion("ZAGJ >", value, "zagj");
			return (Criteria) this;
		}

		public Criteria andZagjGreaterThanOrEqualTo(String value) {
			addCriterion("ZAGJ >=", value, "zagj");
			return (Criteria) this;
		}

		public Criteria andZagjLessThan(String value) {
			addCriterion("ZAGJ <", value, "zagj");
			return (Criteria) this;
		}

		public Criteria andZagjLessThanOrEqualTo(String value) {
			addCriterion("ZAGJ <=", value, "zagj");
			return (Criteria) this;
		}

		public Criteria andZagjLike(String value) {
			addCriterion("ZAGJ like", value, "zagj");
			return (Criteria) this;
		}

		public Criteria andZagjNotLike(String value) {
			addCriterion("ZAGJ not like", value, "zagj");
			return (Criteria) this;
		}

		public Criteria andZagjIn(List<String> values) {
			addCriterion("ZAGJ in", values, "zagj");
			return (Criteria) this;
		}

		public Criteria andZagjNotIn(List<String> values) {
			addCriterion("ZAGJ not in", values, "zagj");
			return (Criteria) this;
		}

		public Criteria andZagjBetween(String value1, String value2) {
			addCriterion("ZAGJ between", value1, value2, "zagj");
			return (Criteria) this;
		}

		public Criteria andZagjNotBetween(String value1, String value2) {
			addCriterion("ZAGJ not between", value1, value2, "zagj");
			return (Criteria) this;
		}

		public Criteria andZddwxzbIsNull() {
			addCriterion("ZDDWXZB is null");
			return (Criteria) this;
		}

		public Criteria andZddwxzbIsNotNull() {
			addCriterion("ZDDWXZB is not null");
			return (Criteria) this;
		}

		public Criteria andZddwxzbEqualTo(String value) {
			addCriterion("ZDDWXZB =", value, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwxzbNotEqualTo(String value) {
			addCriterion("ZDDWXZB <>", value, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwxzbGreaterThan(String value) {
			addCriterion("ZDDWXZB >", value, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwxzbGreaterThanOrEqualTo(String value) {
			addCriterion("ZDDWXZB >=", value, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwxzbLessThan(String value) {
			addCriterion("ZDDWXZB <", value, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwxzbLessThanOrEqualTo(String value) {
			addCriterion("ZDDWXZB <=", value, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwxzbLike(String value) {
			addCriterion("ZDDWXZB like", value, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwxzbNotLike(String value) {
			addCriterion("ZDDWXZB not like", value, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwxzbIn(List<String> values) {
			addCriterion("ZDDWXZB in", values, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwxzbNotIn(List<String> values) {
			addCriterion("ZDDWXZB not in", values, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwxzbBetween(String value1, String value2) {
			addCriterion("ZDDWXZB between", value1, value2, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwxzbNotBetween(String value1, String value2) {
			addCriterion("ZDDWXZB not between", value1, value2, "zddwxzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbIsNull() {
			addCriterion("ZDDWYZB is null");
			return (Criteria) this;
		}

		public Criteria andZddwyzbIsNotNull() {
			addCriterion("ZDDWYZB is not null");
			return (Criteria) this;
		}

		public Criteria andZddwyzbEqualTo(String value) {
			addCriterion("ZDDWYZB =", value, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbNotEqualTo(String value) {
			addCriterion("ZDDWYZB <>", value, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbGreaterThan(String value) {
			addCriterion("ZDDWYZB >", value, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbGreaterThanOrEqualTo(String value) {
			addCriterion("ZDDWYZB >=", value, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbLessThan(String value) {
			addCriterion("ZDDWYZB <", value, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbLessThanOrEqualTo(String value) {
			addCriterion("ZDDWYZB <=", value, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbLike(String value) {
			addCriterion("ZDDWYZB like", value, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbNotLike(String value) {
			addCriterion("ZDDWYZB not like", value, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbIn(List<String> values) {
			addCriterion("ZDDWYZB in", values, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbNotIn(List<String> values) {
			addCriterion("ZDDWYZB not in", values, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbBetween(String value1, String value2) {
			addCriterion("ZDDWYZB between", value1, value2, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZddwyzbNotBetween(String value1, String value2) {
			addCriterion("ZDDWYZB not between", value1, value2, "zddwyzb");
			return (Criteria) this;
		}

		public Criteria andZfajbsIsNull() {
			addCriterion("ZFAJBS is null");
			return (Criteria) this;
		}

		public Criteria andZfajbsIsNotNull() {
			addCriterion("ZFAJBS is not null");
			return (Criteria) this;
		}

		public Criteria andZfajbsEqualTo(Integer value) {
			addCriterion("ZFAJBS =", value, "zfajbs");
			return (Criteria) this;
		}

		public Criteria andZfajbsNotEqualTo(Integer value) {
			addCriterion("ZFAJBS <>", value, "zfajbs");
			return (Criteria) this;
		}

		public Criteria andZfajbsGreaterThan(Integer value) {
			addCriterion("ZFAJBS >", value, "zfajbs");
			return (Criteria) this;
		}

		public Criteria andZfajbsGreaterThanOrEqualTo(Integer value) {
			addCriterion("ZFAJBS >=", value, "zfajbs");
			return (Criteria) this;
		}

		public Criteria andZfajbsLessThan(Integer value) {
			addCriterion("ZFAJBS <", value, "zfajbs");
			return (Criteria) this;
		}

		public Criteria andZfajbsLessThanOrEqualTo(Integer value) {
			addCriterion("ZFAJBS <=", value, "zfajbs");
			return (Criteria) this;
		}

		public Criteria andZfajbsIn(List<Integer> values) {
			addCriterion("ZFAJBS in", values, "zfajbs");
			return (Criteria) this;
		}

		public Criteria andZfajbsNotIn(List<Integer> values) {
			addCriterion("ZFAJBS not in", values, "zfajbs");
			return (Criteria) this;
		}

		public Criteria andZfajbsBetween(Integer value1, Integer value2) {
			addCriterion("ZFAJBS between", value1, value2, "zfajbs");
			return (Criteria) this;
		}

		public Criteria andZfajbsNotBetween(Integer value1, Integer value2) {
			addCriterion("ZFAJBS not between", value1, value2, "zfajbs");
			return (Criteria) this;
		}

		public Criteria andJamsIsNull() {
			addCriterion("JAMS is null");
			return (Criteria) this;
		}

		public Criteria andJamsIsNotNull() {
			addCriterion("JAMS is not null");
			return (Criteria) this;
		}

		public Criteria andJamsEqualTo(String value) {
			addCriterion("JAMS =", value, "jams");
			return (Criteria) this;
		}

		public Criteria andJamsNotEqualTo(String value) {
			addCriterion("JAMS <>", value, "jams");
			return (Criteria) this;
		}

		public Criteria andJamsGreaterThan(String value) {
			addCriterion("JAMS >", value, "jams");
			return (Criteria) this;
		}

		public Criteria andJamsGreaterThanOrEqualTo(String value) {
			addCriterion("JAMS >=", value, "jams");
			return (Criteria) this;
		}

		public Criteria andJamsLessThan(String value) {
			addCriterion("JAMS <", value, "jams");
			return (Criteria) this;
		}

		public Criteria andJamsLessThanOrEqualTo(String value) {
			addCriterion("JAMS <=", value, "jams");
			return (Criteria) this;
		}

		public Criteria andJamsLike(String value) {
			addCriterion("JAMS like", value, "jams");
			return (Criteria) this;
		}

		public Criteria andJamsNotLike(String value) {
			addCriterion("JAMS not like", value, "jams");
			return (Criteria) this;
		}

		public Criteria andJamsIn(List<String> values) {
			addCriterion("JAMS in", values, "jams");
			return (Criteria) this;
		}

		public Criteria andJamsNotIn(List<String> values) {
			addCriterion("JAMS not in", values, "jams");
			return (Criteria) this;
		}

		public Criteria andJamsBetween(String value1, String value2) {
			addCriterion("JAMS between", value1, value2, "jams");
			return (Criteria) this;
		}

		public Criteria andJamsNotBetween(String value1, String value2) {
			addCriterion("JAMS not between", value1, value2, "jams");
			return (Criteria) this;
		}

		public Criteria andJarIsNull() {
			addCriterion("JAR is null");
			return (Criteria) this;
		}

		public Criteria andJarIsNotNull() {
			addCriterion("JAR is not null");
			return (Criteria) this;
		}

		public Criteria andJarEqualTo(String value) {
			addCriterion("JAR =", value, "jar");
			return (Criteria) this;
		}

		public Criteria andJarNotEqualTo(String value) {
			addCriterion("JAR <>", value, "jar");
			return (Criteria) this;
		}

		public Criteria andJarGreaterThan(String value) {
			addCriterion("JAR >", value, "jar");
			return (Criteria) this;
		}

		public Criteria andJarGreaterThanOrEqualTo(String value) {
			addCriterion("JAR >=", value, "jar");
			return (Criteria) this;
		}

		public Criteria andJarLessThan(String value) {
			addCriterion("JAR <", value, "jar");
			return (Criteria) this;
		}

		public Criteria andJarLessThanOrEqualTo(String value) {
			addCriterion("JAR <=", value, "jar");
			return (Criteria) this;
		}

		public Criteria andJarLike(String value) {
			addCriterion("JAR like", value, "jar");
			return (Criteria) this;
		}

		public Criteria andJarNotLike(String value) {
			addCriterion("JAR not like", value, "jar");
			return (Criteria) this;
		}

		public Criteria andJarIn(List<String> values) {
			addCriterion("JAR in", values, "jar");
			return (Criteria) this;
		}

		public Criteria andJarNotIn(List<String> values) {
			addCriterion("JAR not in", values, "jar");
			return (Criteria) this;
		}

		public Criteria andJarBetween(String value1, String value2) {
			addCriterion("JAR between", value1, value2, "jar");
			return (Criteria) this;
		}

		public Criteria andJarNotBetween(String value1, String value2) {
			addCriterion("JAR not between", value1, value2, "jar");
			return (Criteria) this;
		}

		public Criteria andJasjIsNull() {
			addCriterion("JASJ is null");
			return (Criteria) this;
		}

		public Criteria andJasjIsNotNull() {
			addCriterion("JASJ is not null");
			return (Criteria) this;
		}

		public Criteria andJasjEqualTo(Date value) {
			addCriterionForJDBCDate("JASJ =", value, "jasj");
			return (Criteria) this;
		}

		public Criteria andJasjNotEqualTo(Date value) {
			addCriterionForJDBCDate("JASJ <>", value, "jasj");
			return (Criteria) this;
		}

		public Criteria andJasjGreaterThan(Date value) {
			addCriterionForJDBCDate("JASJ >", value, "jasj");
			return (Criteria) this;
		}

		public Criteria andJasjGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("JASJ >=", value, "jasj");
			return (Criteria) this;
		}

		public Criteria andJasjLessThan(Date value) {
			addCriterionForJDBCDate("JASJ <", value, "jasj");
			return (Criteria) this;
		}

		public Criteria andJasjLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("JASJ <=", value, "jasj");
			return (Criteria) this;
		}

		public Criteria andJasjIn(List<Date> values) {
			addCriterionForJDBCDate("JASJ in", values, "jasj");
			return (Criteria) this;
		}

		public Criteria andJasjNotIn(List<Date> values) {
			addCriterionForJDBCDate("JASJ not in", values, "jasj");
			return (Criteria) this;
		}

		public Criteria andJasjBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("JASJ between", value1, value2, "jasj");
			return (Criteria) this;
		}

		public Criteria andJasjNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("JASJ not between", value1, value2, "jasj");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdIsNull() {
			addCriterion("T_PATROL_AREA_ID is null");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdIsNotNull() {
			addCriterion("T_PATROL_AREA_ID is not null");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdEqualTo(Integer value) {
			addCriterion("T_PATROL_AREA_ID =", value, "tPatrolAreaId");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdNotEqualTo(Integer value) {
			addCriterion("T_PATROL_AREA_ID <>", value, "tPatrolAreaId");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdGreaterThan(Integer value) {
			addCriterion("T_PATROL_AREA_ID >", value, "tPatrolAreaId");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("T_PATROL_AREA_ID >=", value, "tPatrolAreaId");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdLessThan(Integer value) {
			addCriterion("T_PATROL_AREA_ID <", value, "tPatrolAreaId");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdLessThanOrEqualTo(Integer value) {
			addCriterion("T_PATROL_AREA_ID <=", value, "tPatrolAreaId");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdIn(List<Integer> values) {
			addCriterion("T_PATROL_AREA_ID in", values, "tPatrolAreaId");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdNotIn(List<Integer> values) {
			addCriterion("T_PATROL_AREA_ID not in", values, "tPatrolAreaId");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdBetween(Integer value1, Integer value2) {
			addCriterion("T_PATROL_AREA_ID between", value1, value2,
					"tPatrolAreaId");
			return (Criteria) this;
		}

		public Criteria andTPatrolAreaIdNotBetween(Integer value1,
				Integer value2) {
			addCriterion("T_PATROL_AREA_ID not between", value1, value2,
					"tPatrolAreaId");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdIsNull() {
			addCriterion("T_COMMUNITY_AREA_ID is null");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdIsNotNull() {
			addCriterion("T_COMMUNITY_AREA_ID is not null");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdEqualTo(Integer value) {
			addCriterion("T_COMMUNITY_AREA_ID =", value, "tCommunityAreaId");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdNotEqualTo(Integer value) {
			addCriterion("T_COMMUNITY_AREA_ID <>", value, "tCommunityAreaId");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdGreaterThan(Integer value) {
			addCriterion("T_COMMUNITY_AREA_ID >", value, "tCommunityAreaId");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("T_COMMUNITY_AREA_ID >=", value, "tCommunityAreaId");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdLessThan(Integer value) {
			addCriterion("T_COMMUNITY_AREA_ID <", value, "tCommunityAreaId");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdLessThanOrEqualTo(Integer value) {
			addCriterion("T_COMMUNITY_AREA_ID <=", value, "tCommunityAreaId");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdIn(List<Integer> values) {
			addCriterion("T_COMMUNITY_AREA_ID in", values, "tCommunityAreaId");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdNotIn(List<Integer> values) {
			addCriterion("T_COMMUNITY_AREA_ID not in", values,
					"tCommunityAreaId");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdBetween(Integer value1,
				Integer value2) {
			addCriterion("T_COMMUNITY_AREA_ID between", value1, value2,
					"tCommunityAreaId");
			return (Criteria) this;
		}

		public Criteria andTCommunityAreaIdNotBetween(Integer value1,
				Integer value2) {
			addCriterion("T_COMMUNITY_AREA_ID not between", value1, value2,
					"tCommunityAreaId");
			return (Criteria) this;
		}

		public Criteria andMarkIsNull() {
			addCriterion("MARK is null");
			return (Criteria) this;
		}

		public Criteria andMarkIsNotNull() {
			addCriterion("MARK is not null");
			return (Criteria) this;
		}

		public Criteria andMarkEqualTo(Boolean value) {
			addCriterion("MARK =", value, "mark");
			return (Criteria) this;
		}

		public Criteria andMarkNotEqualTo(Boolean value) {
			addCriterion("MARK <>", value, "mark");
			return (Criteria) this;
		}

		public Criteria andMarkGreaterThan(Boolean value) {
			addCriterion("MARK >", value, "mark");
			return (Criteria) this;
		}

		public Criteria andMarkGreaterThanOrEqualTo(Boolean value) {
			addCriterion("MARK >=", value, "mark");
			return (Criteria) this;
		}

		public Criteria andMarkLessThan(Boolean value) {
			addCriterion("MARK <", value, "mark");
			return (Criteria) this;
		}

		public Criteria andMarkLessThanOrEqualTo(Boolean value) {
			addCriterion("MARK <=", value, "mark");
			return (Criteria) this;
		}

		public Criteria andMarkIn(List<Boolean> values) {
			addCriterion("MARK in", values, "mark");
			return (Criteria) this;
		}

		public Criteria andMarkNotIn(List<Boolean> values) {
			addCriterion("MARK not in", values, "mark");
			return (Criteria) this;
		}

		public Criteria andMarkBetween(Boolean value1, Boolean value2) {
			addCriterion("MARK between", value1, value2, "mark");
			return (Criteria) this;
		}

		public Criteria andMarkNotBetween(Boolean value1, Boolean value2) {
			addCriterion("MARK not between", value1, value2, "mark");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdIsNull() {
			addCriterion("MARK_USER_ID is null");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdIsNotNull() {
			addCriterion("MARK_USER_ID is not null");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdEqualTo(Integer value) {
			addCriterion("MARK_USER_ID =", value, "markUserId");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdNotEqualTo(Integer value) {
			addCriterion("MARK_USER_ID <>", value, "markUserId");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdGreaterThan(Integer value) {
			addCriterion("MARK_USER_ID >", value, "markUserId");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("MARK_USER_ID >=", value, "markUserId");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdLessThan(Integer value) {
			addCriterion("MARK_USER_ID <", value, "markUserId");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdLessThanOrEqualTo(Integer value) {
			addCriterion("MARK_USER_ID <=", value, "markUserId");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdIn(List<Integer> values) {
			addCriterion("MARK_USER_ID in", values, "markUserId");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdNotIn(List<Integer> values) {
			addCriterion("MARK_USER_ID not in", values, "markUserId");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdBetween(Integer value1, Integer value2) {
			addCriterion("MARK_USER_ID between", value1, value2, "markUserId");
			return (Criteria) this;
		}

		public Criteria andMarkUserIdNotBetween(Integer value1, Integer value2) {
			addCriterion("MARK_USER_ID not between", value1, value2,
					"markUserId");
			return (Criteria) this;
		}

		public Criteria andMarkTimeIsNull() {
			addCriterion("MARK_TIME is null");
			return (Criteria) this;
		}

		public Criteria andMarkTimeIsNotNull() {
			addCriterion("MARK_TIME is not null");
			return (Criteria) this;
		}

		public Criteria andMarkTimeEqualTo(Date value) {
			addCriterionForJDBCDate("MARK_TIME =", value, "markTime");
			return (Criteria) this;
		}

		public Criteria andMarkTimeNotEqualTo(Date value) {
			addCriterionForJDBCDate("MARK_TIME <>", value, "markTime");
			return (Criteria) this;
		}

		public Criteria andMarkTimeGreaterThan(Date value) {
			addCriterionForJDBCDate("MARK_TIME >", value, "markTime");
			return (Criteria) this;
		}

		public Criteria andMarkTimeGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("MARK_TIME >=", value, "markTime");
			return (Criteria) this;
		}

		public Criteria andMarkTimeLessThan(Date value) {
			addCriterionForJDBCDate("MARK_TIME <", value, "markTime");
			return (Criteria) this;
		}

		public Criteria andMarkTimeLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("MARK_TIME <=", value, "markTime");
			return (Criteria) this;
		}

		public Criteria andMarkTimeIn(List<Date> values) {
			addCriterionForJDBCDate("MARK_TIME in", values, "markTime");
			return (Criteria) this;
		}

		public Criteria andMarkTimeNotIn(List<Date> values) {
			addCriterionForJDBCDate("MARK_TIME not in", values, "markTime");
			return (Criteria) this;
		}

		public Criteria andMarkTimeBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("MARK_TIME between", value1, value2,
					"markTime");
			return (Criteria) this;
		}

		public Criteria andMarkTimeNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("MARK_TIME not between", value1, value2,
					"markTime");
			return (Criteria) this;
		}

		public Criteria andGpsConfigIsNull() {
			addCriterion("GPS_CONFIG is null");
			return (Criteria) this;
		}

		public Criteria andGpsConfigIsNotNull() {
			addCriterion("GPS_CONFIG is not null");
			return (Criteria) this;
		}

		public Criteria andGpsConfigEqualTo(String value) {
			addCriterion("GPS_CONFIG =", value, "gpsConfig");
			return (Criteria) this;
		}

		public Criteria andGpsConfigNotEqualTo(String value) {
			addCriterion("GPS_CONFIG <>", value, "gpsConfig");
			return (Criteria) this;
		}

		public Criteria andGpsConfigGreaterThan(String value) {
			addCriterion("GPS_CONFIG >", value, "gpsConfig");
			return (Criteria) this;
		}

		public Criteria andGpsConfigGreaterThanOrEqualTo(String value) {
			addCriterion("GPS_CONFIG >=", value, "gpsConfig");
			return (Criteria) this;
		}

		public Criteria andGpsConfigLessThan(String value) {
			addCriterion("GPS_CONFIG <", value, "gpsConfig");
			return (Criteria) this;
		}

		public Criteria andGpsConfigLessThanOrEqualTo(String value) {
			addCriterion("GPS_CONFIG <=", value, "gpsConfig");
			return (Criteria) this;
		}

		public Criteria andGpsConfigLike(String value) {
			addCriterion("GPS_CONFIG like", value, "gpsConfig");
			return (Criteria) this;
		}

		public Criteria andGpsConfigNotLike(String value) {
			addCriterion("GPS_CONFIG not like", value, "gpsConfig");
			return (Criteria) this;
		}

		public Criteria andGpsConfigIn(List<String> values) {
			addCriterion("GPS_CONFIG in", values, "gpsConfig");
			return (Criteria) this;
		}

		public Criteria andGpsConfigNotIn(List<String> values) {
			addCriterion("GPS_CONFIG not in", values, "gpsConfig");
			return (Criteria) this;
		}

		public Criteria andGpsConfigBetween(String value1, String value2) {
			addCriterion("GPS_CONFIG between", value1, value2, "gpsConfig");
			return (Criteria) this;
		}

		public Criteria andGpsConfigNotBetween(String value1, String value2) {
			addCriterion("GPS_CONFIG not between", value1, value2, "gpsConfig");
			return (Criteria) this;
		}
	}

	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}
	}

	public static class Criterion {
		private String condition;

		private Object value;

		private Object secondValue;

		private boolean noValue;

		private boolean singleValue;

		private boolean betweenValue;

		private boolean listValue;

		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}
}