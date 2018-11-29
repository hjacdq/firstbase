package com.hj.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis {

	private static final Logger log = Logger.getLogger(Redis.class);

	private JedisPool pool;

	private static Redis redis;

	public Redis(JedisPool jedisPool) {
		pool = jedisPool;
		redis = this;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	public static Jedis getJedis() {
		try {
			return redis.pool.getResource();
		} catch (Exception e) {
			log.error("getJedis().....", e);
		}
		return null;
	}

	public static long size(String key) {
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				return j.llen(key);
			}
		} catch (Exception e) {
			log.error("size()...", e);
		} finally {
			close(j);
		}
		return 0L;
	}

	public static void sadd(String key, String... vals) {

		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				j.sadd(key, vals);
			}
		} catch (Exception e) {
			log.error("size()...", e);
		} finally {
			close(j);
		}
	}

	/**
	 * 返回集合中的所有成员
	 *
	 * @param String
	 *            key
	 * @return 成员集合
	 */
	public static Set<String> smembers(String key) {
		Set<String> set = null;

		Jedis j = null;
		try {
			j = getJedis();
			if (j != null)
				set = j.smembers(key);
		} catch (Exception e) {
			log.error("size()...", e);
		} finally {
			close(j);
		}
		return set;
	}

	public static boolean exists(String key) {
		if (key == null)
			return false;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				return j.exists(key.getBytes());
			}
		} catch (Exception e) {
			log.error("exists()...", e);
		} finally {
			close(j);
		}
		return false;
	}

	public static long setnx(String key, String value, int seconds) {
		if (key == null)
			return -1;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				long result = j.setnx(key, value);
				if (result > 0) {
					j.expire(key, seconds);
				}
				return result;
			}
		} catch (Exception e) {

		} finally {
			close(j);
		}
		return -1;
	}

	public static Object pop(String key) {
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				byte[] b = j.rpop(key.getBytes());
				Object obj = bytes2Object(b);
				return obj;
			}
		} catch (Exception e) {
			log.error("pop()...", e);
		} finally {
			close(j);
		}
		return null;
	}

	/**
	 * 清空队列
	 * 
	 * @param key
	 *            队列名
	 * @return true:成功;false:失败
	 */
	public static boolean ltrim(String key) {
		if (key == null)
			return false;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				String result = j.ltrim(key, 1, 0);
				return "OK".equalsIgnoreCase(result);
			}
		} catch (Exception e) {
			log.error("ltrim()...", e);
		} finally {
			close(j);
		}
		return false;
	}

	/**
	 * 获取队列长度
	 * 
	 * @param key
	 *            队列名
	 * @return 队列长度
	 */
	public static Long llen(String key) {
		if (key == null)
			return null;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				return j.llen(key);
			}
		} catch (Exception e) {
			log.error("llen()...", e);
		} finally {
			close(j);
		}
		return null;
	}

	public static Object rpop(String key) {
		if (key == null)
			return null;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				byte[] b = j.rpop(key.getBytes());
				return bytes2Object(b);
			}
		} catch (Exception e) {
			log.error("rpop()...", e);
		} finally {
			close(j);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T lpop(String key) {

		if (key == null)
			return null;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				return (T) bytes2Object(j.lpop(key.getBytes()));
			}
		} catch (Exception e) {
			log.error("lpop()...", e);
		} finally {
			close(j);
		}
		return null;
	}

	public static void lpush(String key, Object value) {
		if (key == null || value == null)
			return;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				j.lpush(key.getBytes(), object2Bytes(value));
			}
		} catch (Exception e) {
			log.error("lpush()...", e);
		} finally {
			close(j);
		}

	}

	public static void rpush(String key, Object value) {
		if (key == null || value == null)
			return;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				j.rpush(key.getBytes(), object2Bytes(value));
			}
		} catch (Exception e) {
			log.error("rpush()...", e);
		} finally {
			close(j);
		}

	}

	/**
	 * 获取key的到期剩余时间（单位秒）
	 * 
	 * @param key
	 * @return
	 */
	public static Long ttl(String key) {
		if (key == null)
			return -1L;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				return j.ttl(key.getBytes());
			}
		} catch (Exception e) {
			log.error("ttl()...", e);
		} finally {
			close(j);
		}
		return -1L;
	}

	/**
	 * 重新设置key对应的value值。 并保持原先的过期时间（如果有过期时间的话）
	 * 
	 * @param key
	 * @param value
	 */
	public static void reset(String key, Object value) {
		if (key == null || value == null)
			return;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				long t = j.ttl(key.getBytes());
				j.del(key.getBytes());
				j.set(key.getBytes(), object2Bytes(value));
				if (t > 0) {
					j.expire(key.getBytes(), (int) t);
				}
			}

		} catch (Exception e) {
			log.error("reset()...", e);
		} finally {
			close(j);
		}

	}

	/**
	 * 重新设置值和过期时间
	 * 
	 * @param key
	 * @param value
	 * @param ttlseconds
	 */
	public static void resetex(String key, Object value, Integer ttlseconds) {
		if (key == null || ttlseconds == null)
			return;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				j.del(key.getBytes());
				j.set(key.getBytes(), object2Bytes(value));
				j.expire(key.getBytes(), ttlseconds);
			}

		} catch (Exception e) {
			log.error("resetex()...", e);
		} finally {
			close(j);
		}

	}

	/**
	 * 获取key 根据正则
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> keys(String key) {
		Jedis j = null;
		Set<String> keys = null;
		try {
			j = getJedis();
			keys = new HashSet<String>();
			keys.addAll(j.keys(key));
		} catch (Exception e) {
			log.error("keys()...", e);
		} finally {
			close(j);
		}
		return keys;
	}

	/**
	 * 设置key的过期时间（单位秒）
	 * 
	 * @param key
	 * @param value
	 */
	public static void expire(String key, Integer ttlseconds) {
		if (key == null || ttlseconds == null)
			return;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				j.expire(key.getBytes(), ttlseconds);
			}

		} catch (Exception e) {
			log.error("expire()...", e);
		} finally {
			close(j);
		}

	}

	public static boolean delete(String key) {
		if (key == null)
			return false;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				return j.del(key.getBytes()) > 0;
			}
		} catch (Exception e) {
			log.error("delete()...", e);
		} finally {
			close(j);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String key) {

		if (key == null)
			return null;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				return (T) bytes2Object(j.get(key.getBytes()));
			}

		} catch (Exception e) {
			log.error("get()...", e);
		} finally {
			close(j);
		}
		return null;
	}

	public static String getString(String key) {

		if (key == null)
			return null;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				if (j.get(key.getBytes()) != null)
					return new String(j.get(key.getBytes()));
				return null;
			}

		} catch (Exception e) {
			log.error("", e);
		} finally {
			close(j);
		}
		return null;
	}

	public static void put(String key, Object value) {
		if (key == null || value == null)
			return;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				j.set(key.getBytes(), object2Bytes(value));
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			close(j);
		}

	}

	public static void putString(String key, String value) {
		if (key == null || value == null)
			return;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				j.set(key.getBytes(), value.getBytes());
			}
		} catch (Exception e) {
			log.error("putString()...", e);
		} finally {
			close(j);
		}

	}

	private static Object bytes2Object(byte[] objBytes) {
		if (objBytes == null || objBytes.length == 0) {
			return null;
		}
		ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
		ObjectInputStream oi;
		try {
			oi = new ObjectInputStream(bi);
			return (Object) oi.readObject();
		} catch (IOException e) {
			log.error("bytes2Object()...", e);
		} catch (ClassNotFoundException e) {
			log.error("bytes2Object()...", e);
		}
		return null;
	}

	private static byte[] object2Bytes(Object obj) {
		if (obj == null) {
			return null;
		}
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo;
		try {
			oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			return bo.toByteArray();
		} catch (IOException e) {
			log.error("object2Bytes()...", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T lrange(String key, long start, long end) {

		if (key == null)
			return null;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				List<byte[]> l = j.lrange(key.getBytes(), start, end);
				List<Object> ls = new ArrayList<Object>();
				if (l != null && l.size() > 0) {
					for (byte[] b : l) {
						ls.add(bytes2Object(b));
					}
				}
				return (T) ls;
			}

		} catch (Exception e) {
			log.error("get()...", e);
		} finally {
			close(j);
		}
		return null;
	}

	public static Long setnx(final String key, final String value) {
		Jedis jedis = getJedis();

		return jedis.setnx(key, value);
	}

	public static void close(Jedis jedis) {
		if (jedis != null)
			jedis.close();
	}

	/**
	 * 获取下一个值,在原有值+1
	 * 
	 * @param key
	 * @return
	 */
	public static Long incr(String key) {
		if (key == null)
			return null;
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				return j.incr(key.getBytes());
			}

		} catch (Exception e) {
			log.error("", e);
		} finally {
			close(j);
		}
		return null;
	}

	/**
	 * redis set操作,不进行编码
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value) {
		if (key == null || value == null) {
			return;
		}
		Jedis j = null;
		try {
			j = getJedis();
			if (j != null) {
				j.set(key, value);
			}

		} catch (Exception e) {
			log.error("", e);
		} finally {
			close(j);
		}
	}
}
