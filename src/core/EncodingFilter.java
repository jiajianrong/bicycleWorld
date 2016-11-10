package core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodingFilter implements Filter {
	protected String encoding = null;

	protected FilterConfig filterConfig = null;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// Select and set (if needed) the character encoding to be used 
		String encoding = selectEncoding(request);
		if (encoding != null) {
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		
		// -----------------
		// get url param
		// 使用自定义HttpServletRequestWrapper的ServletRequest
		// -----------------
		HttpServletRequest req = (HttpServletRequest)request;
        if(req.getMethod().equalsIgnoreCase("get"))
            req = new GetHttpServletRequestWrapper(req, "UTF-8");
		
		// Pass control on to the next filter 
        // 2016-11-10 jiajianrong
		// chain.doFilter(request, response);
		chain.doFilter(req, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 2016-11-10
	 * @author jiajianrong@58.com
	 * 重写HttpServletRequestWrapper.getParameter()
	 */
	class GetHttpServletRequestWrapper extends HttpServletRequestWrapper {  
		  
	    private String charset = encoding; // "UTF-8";  
	  
	    public GetHttpServletRequestWrapper(HttpServletRequest request) {  
	        super(request);  
	    }  
	  
	    /** 
	     * 获得被装饰对象的引用和采用的字符编码 
	     * @param request 
	     * @param charset 
	     */  
	    public GetHttpServletRequestWrapper(HttpServletRequest request, String charset) {  
	        super(request);  
	        this.charset = charset;  
	    }  
	  
	    /** 
	     * 实际上就是调用被包装的请求对象的getParameter方法获得参数，然后再进行编码转换 
	     */  
	    public String getParameter(String name) {  
	        String value = super.getParameter(name);  
	        value = value == null ? null : convert(value);  
	        return value;  
	    }  
	  
	    public String convert(String target) {
	        try {  
	            return new String(target.trim().getBytes("ISO-8859-1"), charset);  
	        } catch (UnsupportedEncodingException e) {  
	            return target;  
	        }  
	    }  
	  
	}  
}
