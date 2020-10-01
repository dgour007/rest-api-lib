package com.omantel.restapi.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.omantel.restapi.bean.Ldap;

public class LdapUtil {
	
	/*public static void main (String[] args) {
		getUserDetailsFromLdap("69749");
	}*/
	
	public static Ldap getUserDetailsFromLdap(String userId) {
		Ldap ldapUser = null;
		//---OMCORP Domain---#
        /*String ldapAdServer = "ldap://10.164.103.20:389/";
        String ldapSearchBase = "OU=OTG,DC=OTG,DC=OM";
        String ldapUsername = "CN=CRM LDAP,OU=CRM Service Account,OU=Service Accounts,DC=OTG,DC=OM";//"70003@omcorp";
        String ldapPassword = "COMPass#032018";*/
		//---OTG Domain---#
        String ldapAdServer = "ldap://10.164.103.20:389/";
        String ldapSearchBase = "OU=OTG,DC=OTG,DC=OM";
        String ldapUsername = "CN=CRM LDAP,OU=CRM Service Account,OU=Service Accounts,DC=OTG,DC=OM";
        String ldapPassword = "COMPass#032018";
        
        String ldapAccountToLookup = userId;
        String name = null, mail = null, mobile = null, initial = null, dept = null, manager = null;
        
        try {
        	
        	Hashtable<String, Object> env = new Hashtable<String, Object>();
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
            env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, ldapAdServer);
            
            //ensures that objectSID attribute values
            //will be returned as a byte[] instead of a String
            env.put("java.naming.ldap.attributes.binary", "objectSID");
            
            // the following is helpful in debugging errors
            //env.put("com.sun.jndi.ldap.trace.ber", System.err);
            
            //LdapContext ctx = new InitialLdapContext();
            DirContext ctx = new InitialDirContext(env);
            
            //1) lookup the ldap account
            SearchResult srLdapUser = findAccountByAccountName(ctx, ldapSearchBase, ldapAccountToLookup);
            Attributes attr = srLdapUser.getAttributes();
            
            if (attr.get("name")!=null) {
            	name = attr.get("name").toString();
            	name = name.replace("name: ", "");
            }
            
            if (attr.get("mail")!=null) {
            	mail = attr.get("mail").toString();
            	mail = mail.replace("mail: ", "");
            }
            
            if (attr.get("mobile")!=null) {
            	mobile = attr.get("mobile").toString();
            	mobile = mobile.replace("mobile: ", "");
            }
            
            if (attr.get("initials")!=null) {
            	initial = attr.get("initials").toString();
            	initial = initial.replace("initials: ", "");
            }
            
            if (attr.get("manager")!=null) {
            	manager = attr.get("manager").toString();
            	manager = manager.replace("manager: ", "");
            	if (manager.indexOf("CN") != -1 && manager.indexOf(",") != -1) {
            		manager = manager.substring(3,manager.indexOf(","));
            	}
            }
            
            if (attr.get("department")!=null) {
            	dept = attr.get("department").toString();
            	dept = dept.replace("department: ", "");
            }
            
            ldapUser = new Ldap();
            ldapUser.setFullName(name);
            ldapUser.seteMailAddress(mail);
            ldapUser.setMobile(mobile);
            ldapUser.setDepartment(dept);
            ldapUser.setInitials(initial);
            ldapUser.setManager(manager);
            
            //System.out.println(srLdapUser.getAttributes());
            /*System.out.println("name : "+attr.get("name"));
            System.out.println("mail : "+attr.get("mail"));
            System.out.println("mobile : "+attr.get("mobile"));*/
            
            //2) get the SID of the users primary group
            //String primaryGroupSID = ldap.getPrimaryGroupSID(srLdapUser);
            
            //3) get the users Primary Group
            //String primaryGroupName = ldap.findGroupBySID(ctx, ldapSearchBase, primaryGroupSID);
        } catch (NamingException nae) {
        	System.out.println("1"+nae.getMessage());
        } catch (Exception e) {
        	System.out.println("2"+e.getMessage());
        }
        return ldapUser;
    }
    
	private static SearchResult findAccountByAccountName(DirContext ctx, String ldapSearchBase, String accountName) throws NamingException {

        //String searchFilter = "(&(objectClass=user)(sAMAccountName=" + accountName + "))";
		String searchFilter = "(sAMAccountName=" + accountName + ")";

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

        SearchResult searchResult = null;
        if(results.hasMoreElements()) {
             searchResult = (SearchResult) results.nextElement();

            //make sure there is not another item available, there should be only 1 match
            if(results.hasMoreElements()) {
                //System.err.println("Matched multiple users for the accountName: " + accountName);
                return null;
            }
        }
        
        return searchResult;
    }
    
    public String findGroupBySID(DirContext ctx, String ldapSearchBase, String sid) throws NamingException {
        
        String searchFilter = "(&(objectClass=group)(objectSid=" + sid + "))";

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        
        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

        if(results.hasMoreElements()) {
            SearchResult searchResult = (SearchResult) results.nextElement();

            //make sure there is not another item available, there should be only 1 match
            if(results.hasMoreElements()) {
                //System.err.println("Matched multiple groups for the group with SID: " + sid);
                return null;
            } else {
                return (String)searchResult.getAttributes().get("sAMAccountName").get();
            }
        }
        return null;
    }
    
    public String getPrimaryGroupSID(SearchResult srLdapUser) throws NamingException {
        byte[] objectSID = (byte[])srLdapUser.getAttributes().get("objectSid").get();
        String strPrimaryGroupID = (String)srLdapUser.getAttributes().get("primaryGroupID").get();
        
        String strObjectSid = decodeSID(objectSID);
        
        return strObjectSid.substring(0, strObjectSid.lastIndexOf('-') + 1) + strPrimaryGroupID;
    }
    
    /**
     * The binary data is in the form:
     * byte[0] - revision level
     * byte[1] - count of sub-authorities
     * byte[2-7] - 48 bit authority (big-endian)
     * and then count x 32 bit sub authorities (little-endian)
     * 
     * The String value is: S-Revision-Authority-SubAuthority[n]...
     * 
     * Based on code from here - http://forums.oracle.com/forums/thread.jspa?threadID=1155740&tstart=0
     */
    public static String decodeSID(byte[] sid) {
        
        final StringBuilder strSid = new StringBuilder("S-");

        // get version
        final int revision = sid[0];
        strSid.append(Integer.toString(revision));
        
        //next byte is the count of sub-authorities
        final int countSubAuths = sid[1] & 0xFF;
        
        //get the authority
        long authority = 0;
        //String rid = "";
        for(int i = 2; i <= 7; i++) {
           authority |= ((long)sid[i]) << (8 * (5 - (i - 2)));
        }
        strSid.append("-");
        strSid.append(Long.toHexString(authority));
        
        //iterate all the sub-auths
        int offset = 8;
        int size = 4; //4 bytes for each sub auth
        for(int j = 0; j < countSubAuths; j++) {
            long subAuthority = 0;
            for(int k = 0; k < size; k++) {
                subAuthority |= (long)(sid[offset + k] & 0xFF) << (8 * k);
            }
            
            strSid.append("-");
            strSid.append(subAuthority);
            
            offset += size;
        }
        
        return strSid.toString();    
    }	
}
