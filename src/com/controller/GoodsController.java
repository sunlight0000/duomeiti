package com.controller;

import java.io.File;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Ftype;
import com.entity.Goods;
import com.entity.Keep;
import com.entity.Sysuser;
import com.server.FtypeServer;
import com.server.GoodsServer;
import com.server.SysuserServier;
import com.server.KeepServer;
import com.util.PageBean;

@Controller
public class GoodsController {
	@Resource
	private GoodsServer goodsService;
	@Resource
	private FtypeServer typeService;
	@Resource
	private KeepServer keepService;
	@Resource
	private SysuserServier userService;

	// 分页查询--显示个人的资源信息
	@RequestMapping("myGoodsList.do")
	public String myGoodsList(@RequestParam(value = "page", required = false) String page, ModelMap map,
			HttpSession session) {
		Sysuser user = (Sysuser) session.getAttribute("user");
		if (user == null) {
			return "login";
		} else {
			Map<String, Object> umap = new HashMap<>();
			umap.put("uid", user.getUid());
			int num = goodsService.getCount(umap);
			if (num == 0) {
				return "redirect:doAddGoods.do";
			} else {
				if (page == null || page.equals("")) {
					page = "1";
				}
				PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
				Map<String, Object> pmap = new HashMap<String, Object>();
				pmap.put("pageno", pageBean.getStart());
				pmap.put("pageSize", pageBean.getPageSize());
				Map<String, Object> cmap = new HashMap<String, Object>();
				pmap.put("uid", user.getUid());
				cmap.put("uid", user.getUid());
				int total = goodsService.getCount(cmap);
				pageBean.setTotal(total);
				List<Goods> list = goodsService.getByPage(pmap);
				map.put("page", pageBean);
				map.put("list", list);
				map.put("tlist", typeService.getAll(null));
				session.setAttribute("p", 1);
				List<Ftype> tlist = typeService.getAll(null);
				map.put("tlist", tlist);
				return "myGoodslist";
			}
		}
	}

	// 处理添加资源的信息
	@RequestMapping("doAddGoods.do")
	public String doAddGoods(ModelMap map, HttpSession session) {
		Sysuser user = (Sysuser) session.getAttribute("user");
		if (user == null) {
			return "login";
		} else {
			List<Ftype> tlist = typeService.getAll(null);
			map.put("tlist", tlist);
			return "addGoods";
		}
	}

	// 添加资源信息
	@RequestMapping("addGoods.do")
	public String addGoods(@RequestParam(value = "file", required = false) MultipartFile file, String img,
			@RequestParam(value = "file2", required = false) MultipartFile file2, String img2,
			@RequestParam(value = "file3", required = false) MultipartFile file3, String img3,
			HttpServletRequest request, Goods Goods, HttpSession session) {
		System.out.println("ddddd");
		Sysuser u = (Sysuser) session.getAttribute("user");
		System.out.println("u=-=" + u);
		if (u == null || u.equals("")) {
			return "login";
		} else {
			Ftype ftype = typeService.getById(Goods.getFid());
			Goods.setBtype(ftype.getBtype());
			img = fileUpload(file, request, img);
			img2 = fileUpload(file2, request, img2);
			img3 = fileUpload(file3, request, img3);
			if (img != null) {
				Goods.setImg(img);
			}
			if (img2 != null) {
				Goods.setUpload(img2);
			}
			if (img3 != null) {
				Goods.setFiles(img3);
			}
			Goods.setDnum(0);
			Goods.setXnum(0);
			Goods.setIscommend("非推荐");
			java.sql.Timestamp time = new Timestamp(System.currentTimeMillis());
			Goods.setPubtime(time.toString().substring(0, 19));
			Goods.setUid(u.getUid() + "");
			goodsService.add(Goods);
			return "redirect:myGoodsList.do";
		}
	}

	// 处理更新资源的信息
	@RequestMapping("doUpdateGoods.do")
	public String doUpdateGoods(ModelMap map, int id) {
		map.put("goods", goodsService.getById(id));
		List<Ftype> tlist = typeService.getAll(null);
		map.put("tlist", tlist);
		return "update_Goods";
	}

	@RequestMapping("updateGoods.do")
	public String updateGoods(@RequestParam(value = "file", required = false) MultipartFile file,String img,
			@RequestParam(value = "file2", required = false) MultipartFile file2, String img2,
			@RequestParam(value = "file3", required = false) MultipartFile file3, String img3,
			HttpServletRequest request,Goods Goods ) {
		img = fileUpload(file, request, img);
		if (img != null&&!img.equals("zanwu.jpg")) {
			Goods.setImg(img);
		}
		img2 = fileUpload(file2, request, img2);
		if (img2 != null&&!img.equals("zanwu.jpg")) {
			Goods.setUpload(img2);
		}
		img3 = fileUpload(file3, request, img3);
		if(img3!=null&&!img.equals("zanwu.jpg")){
			Goods.setFiles(img3);
		}
		Ftype ftype = typeService.getById(Goods.getFid());
		Goods.setBtype(ftype.getBtype());
		goodsService.update(Goods);
		return "redirect:myGoodsList.do";
	}

	// 显示一类资源
	@RequestMapping("showGoodsType.do")
	public String showGoodsType(ModelMap map, int fid) {
		Map<String, Object> tmap0 = new HashMap<>();
		tmap0.put("fid", fid);
		map.put("list", goodsService.getAll(tmap0));
		map.put("tlist", typeService.getAll(null));
		map.put("ulist", userService.getAll(null));
		List<Ftype> tlist = typeService.getAll(null);
		map.put("tlist", tlist);
		return "goodslist";
	}

	// 显示单个资源的信息
	@RequestMapping("showGoodsx.do")
	public String showGoodsx(ModelMap map, int id) {
		Goods goods = goodsService.getById(id);
		goods.setDnum(goods.getDnum() + 1);
		goodsService.update(goods);
		map.put("goods", goodsService.getById(id));
		map.put("tlist", typeService.getAll(null));
		map.put("ulist", userService.getAll(null));
		List<Ftype> tlist = typeService.getAll(null);
		map.put("tlist", tlist);
		return "goodsx";
	}
	//显示收费资源信息
	@RequestMapping("showGoodsx_shouFei.do")
	public String showGoodsx_shouFei(ModelMap map, int id) {
		Goods goods = goodsService.getById(id);
		goods.setDnum(goods.getDnum() + 1);
		goodsService.update(goods);
		map.put("goods", goodsService.getById(id));
		map.put("tlist", typeService.getAll(null));
		map.put("ulist", userService.getAll(null));
		List<Ftype> tlist = typeService.getAll(null);
		map.put("tlist", tlist);
		return "goodsx_shouFei";
	}
	// 分页---资源信息
	@RequestMapping("showGoodsList.do")
	public String showGoods(@RequestParam(value = "page", required = false) String page, ModelMap map,
			HttpSession session, Goods cd) {
		if (page == null || page.equals("")) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("pageno", pageBean.getStart());
		pmap.put("pageSize", pageBean.getPageSize());
		int total = goodsService.getCount(null);
		pageBean.setTotal(total);
		List<Goods> list = goodsService.getByPage(pmap);
		map.put("page", pageBean);
		map.put("list", list);
		session.setAttribute("p", 1);
		map.put("tlist", typeService.getAll(null));
		map.put("ulist", userService.getAll(null));
		List<Ftype> tlist = typeService.getAll(null);
		map.put("tlist", tlist);
		return "goodslist";
	}

	// 分页---资源信息--推荐
	@RequestMapping("showGoodsList_commend.do")
	public String showGoodsList_commend(@RequestParam(value = "page", required = false) String page, ModelMap map,
			HttpSession session, Goods cd) {
		if (page == null || page.equals("")) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
		Map<String, Object> pmap = new HashMap<String, Object>();
		Map<String, Object> cmap = new HashMap<String, Object>();
		pmap.put("pageno", pageBean.getStart());
		pmap.put("pageSize", pageBean.getPageSize());
		cmap.put("iscommend", "推荐");
		pmap.put("iscommend", "推荐");
		int total = goodsService.getCount(cmap);
		pageBean.setTotal(total);
		List<Goods> list = goodsService.getByPage(pmap);
		map.put("page", pageBean);
		map.put("list", list);
		session.setAttribute("p", 3);
		// map.put("tlist", typeService.getAll(null));
		map.put("ulist", userService.getAll(null));
		List<Ftype> tlist = typeService.getAll(null);
		map.put("tlist", tlist);
		return "goodslist";
	}

	
	// ---资源信息--排行榜--top10
	@RequestMapping("showGoodsList_top_all.do")
	public String showGoodsList_top_all(ModelMap map, HttpSession session, Goods goods) {
		List<Goods> list = goodsService.getTop10(null);
		session.setAttribute("p", 3);
		List<Ftype> tlist = typeService.getAll(null);
		map.put("tlist", tlist);
		map.put("list", list);
		return "goods_top10";
	}

	// 分页模糊查询
	@RequestMapping("selectGoodsList.do")
	public String SelectGoods(@RequestParam(value = "page", required = false) String page, ModelMap map,
			HttpSession session, Goods cd) {
		if (page == null || page.equals("")) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("pageno", pageBean.getStart());
		pmap.put("pageSize", pageBean.getPageSize());

		Map<String, Object> cmap = new HashMap<String, Object>();
		System.out.println("name===" + cd.getName());
		if (cd.getName() != null && !cd.getName().equals("")) {
			cmap.put("name", cd.getName());
			pmap.put("name", cd.getName());

		}
		if (cd.getFid() != null && !cd.getFid().equals("")) {
			cmap.put("fid", cd.getFid());
			pmap.put("fid", cd.getFid());
		}
		int total = goodsService.getCount(cmap);
		pageBean.setTotal(total);
		List<Goods> list = goodsService.getByPage(pmap);
		map.put("page", pageBean);
		map.put("list", list);
		session.setAttribute("p", 2);
		map.put("tlist", typeService.getAll(null));
		List<Ftype> tlist = typeService.getAll(null);
		map.put("tlist", tlist);
		return "goodslist";
	}

	@RequestMapping("deleteGoods.do")
	public String deleteGoods(int id) {
		Map<String, Object> map = new HashMap<>();
		map.put("fid", id);
		List<Keep> klist = keepService.getAll(map);
		Keep keep = new Keep();
		if (klist.size() > 0) {
			for (Keep k : klist) {
				keep = keepService.getById(k.getId());
				keep.setStatus("已删除");
				keepService.update(keep);
			}
		}
		goodsService.delete(id);
		return "redirect:myGoodsList.do";
	}

	/**
	 * 后台资源的处理
	 * 
	 * @param file
	 * @param request
	 * @param img
	 * @return
	 */

	// 处理添加资源的信息
	@RequestMapping("admin/doAddGoods_admin.do")
	public String doAddGoods_admin(ModelMap map, HttpSession session) {
		Sysuser user = (Sysuser) session.getAttribute("auser");
		if (user == null) {
			return "admin/login";
		} else {
			map.put("tlist", typeService.getAll(null));
			return "admin/goods_add";
		}
	}

	// 添加资源信息
	@RequestMapping("admin/addGoods_admin.do")
	public String addGoods_admin(@RequestParam(value = "file", required = false) MultipartFile file, String img,
			@RequestParam(value = "file2", required = false) MultipartFile file2, String img2,
			@RequestParam(value = "file3", required = false) MultipartFile file3, String img3,
			HttpServletRequest request, Goods Goods, HttpSession session) {
		System.out.println("ddddd");
		Sysuser u = (Sysuser) session.getAttribute("auser");
		System.out.println("u=-=" + u);
		if (u == null || u.equals("")) {
			return "admin/login";
		} else {
			img = fileUpload(file, request, img);
			if (img != null) {
				Goods.setImg(img);
			} else {
				Goods.setImg("zanwu.jpg");
			}
			img2 =  fileUpload(file2, request, img2);
			if(img2!=null){
				Goods.setUpload(img2);
			}
			img3 = fileUpload(file3, request, img3);
			if(img3!=null){
				Goods.setFiles(img3);
			}
			Goods.setDnum(0);
			Ftype ftype = typeService.getById(Goods.getFid());
			Goods.setBtype(ftype.getBtype());
			java.sql.Timestamp time = new Timestamp(System.currentTimeMillis());
			Goods.setPubtime(time.toString().substring(0, 19));
			Goods.setUid(u.getUid() + "");
			goodsService.add(Goods);
			return "redirect:goodsList.do";
		}
	}

	// 处理更新资源的信息
	@RequestMapping("admin/doUpdateGoods_admin.do")
	public String doUpdateGoods_admin(ModelMap map, int id) {
		map.put("goods", goodsService.getById(id));
		map.put("tlist", typeService.getAll(null));
		return "admin/goods_update";
	}

	// 处理更新资源的信息
	@RequestMapping("admin/deleteGoods_admin.do")
	public String deleteGoods_admin(ModelMap map, int id) {
		goodsService.delete(id);
		return "redirect:goodsList.do";
	}

	// 资源推荐
	@RequestMapping("admin/doUpdateGoods_tuiJian.do")
	public String doUpdateGoods_tuiJian(ModelMap map, int id) {
		Goods goods = goodsService.getById(id);
		goods.setIscommend("推荐");
		goodsService.update(goods);
		return "redirect:goodsList.do";
	}

	@RequestMapping("admin/updateGoods_admin.do")
	public String updateGoods_admin(@RequestParam(value = "file", required = false) MultipartFile file, String img,
			@RequestParam(value = "file2", required = false) MultipartFile file2, String img2,
			@RequestParam(value = "file3", required = false) MultipartFile file3, String img3,
			HttpServletRequest request, Goods Goods) {
		img = fileUpload(file, request, img);
		if (img != null && !img.equals("zanwu.jpg")) {
			Goods.setImg(img);
		}
		img2 = fileUpload(file2, request, img2);
		if (img2 != null) {
			Goods.setUpload(img2);
		}
		img3 = fileUpload(file3, request, img3);
		if (img3 != null) {
			Goods.setFiles(img3);
		}
		Ftype type = typeService.getById(Goods.getFid());
		Goods.setBtype(type.getBtype());
		goodsService.update(Goods);
		return "redirect:goodsList.do";
	}

	// 分页查询--显示所有的资源信息
	@RequestMapping("admin/goodsList.do")
	public String goodsList(@RequestParam(value = "page", required = false) String page, ModelMap map,
			HttpSession session) {
		Sysuser u = (Sysuser) session.getAttribute("auser");
		System.out.println("u=-=" + u);
		if (u == null || u.equals("")) {
			return "admin/login";
		} else {
		if (page == null || page.equals("")) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("pageno", pageBean.getStart());
		pmap.put("pageSize", pageBean.getPageSize());
		Map<String, Object> cmap = new HashMap<String, Object>();
		if(u.getUtype().equals("老师")){
			pmap.put("uid", u.getUid());
			cmap.put("uid", u.getUid());
		}
		int total = goodsService.getCount(cmap);
		pageBean.setTotal(total);
		List<Goods> list = goodsService.getByPage(pmap);
		map.put("page", pageBean);
		map.put("list", list);
		map.put("tlist", typeService.getAll(null));
		map.put("ulist", userService.getAll(null));
		session.setAttribute("p", 1);
		return "admin/goods_list";
		}
	}

	
	// 分页查询--查询所有的资源信息
	@RequestMapping("admin/goodsList_search.do")
	public String goodsList_search(@RequestParam(value = "page", required = false) String page, ModelMap map,
			HttpSession session, Goods goods) {
		Sysuser u = (Sysuser) session.getAttribute("auser");
		System.out.println("u=-=" + u);
		if (u == null || u.equals("")) {
			return "admin/login";
		} else {
		if (page == null || page.equals("")) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), PageBean.PAGESIZE);
		Map<String, Object> pmap = new HashMap<String, Object>();
		pmap.put("pageno", pageBean.getStart());
		pmap.put("pageSize", pageBean.getPageSize());
		Map<String, Object> cmap = new HashMap<String, Object>();
		if(u.getUtype().equals("老师")){
			pmap.put("uid", u.getUid());
			cmap.put("uid", u.getUid());
		}
		if (goods.getFid() != null && !goods.getFid().equals("")) {
			pmap.put("fid", goods.getFid());
			cmap.put("fid", goods.getFid());
		}
		if (goods.getName() != null && !goods.getName().equals("")) {
			pmap.put("name", goods.getName());
			cmap.put("name", goods.getName());
		}
		int total = goodsService.getCount(cmap);
		pageBean.setTotal(total);
		List<Goods> list = goodsService.getByPage(pmap);
		map.put("page", pageBean);
		map.put("list", list);
		map.put("tlist", typeService.getAll(null));
		map.put("ulist", userService.getAll(null));
		session.setAttribute("p", 2);
		return "admin/goods_list";
		}
	}

	// 文件上传
	public String fileUpload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, String img) {
		String path = request.getSession().getServletContext().getRealPath("upload");
		String fileName = file.getOriginalFilename();
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String pa = request.getContextPath() + "/upload/" + fileName;
		System.out.println("path===" + pa);
		if (fileName != null && !fileName.equals("")) {
			img = fileName;
		} else {
			img = "zanwu.jpg";
		}
		return img;

	}
}
