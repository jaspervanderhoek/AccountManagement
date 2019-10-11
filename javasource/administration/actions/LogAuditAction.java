// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package administration.actions;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixIdentifier;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;

import administration.proxies.Account;
import administration.proxies.AuditRecord;

public class LogAuditAction extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String ActionDescription;
	private IMendixObject ActionAppliedToObject;

	public LogAuditAction(IContext context, java.lang.String ActionDescription, IMendixObject ActionAppliedToObject)
	{
		super(context);
		this.ActionDescription = ActionDescription;
		this.ActionAppliedToObject = ActionAppliedToObject;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		// BEGIN USER CODE
		
		IContext originalContext = this.getContext(),
				context = Core.createSystemContext();
		
		IMendixObject user = originalContext.getCurrentUserObject();
		String userName = "(system)";
		Long userGUID = null;
		IMendixIdentifier userMxId = null;
		if( user != null ) {
			userName = user.getValue(originalContext, "Name");
			userGUID = user.getId().toLong();
			if( Account.entityName.equals(user.getType()) || Core.isSubClassOf(Account.entityName, user.getType()) ) {
				userMxId = user.getId();
			}
		}
		
		IMendixObject auditRecord = Core.instantiate(context, AuditRecord.entityName);
		auditRecord.setValue(context, AuditRecord.MemberNames.Action.toString(), this.ActionDescription);
		if( this.ActionAppliedToObject != null )
			auditRecord.setValue(context, AuditRecord.MemberNames.AppliedToObjectGUID.toString(), this.ActionAppliedToObject.getId().toLong());
		auditRecord.setValue(context, AuditRecord.MemberNames.AuditRecord_Account.toString(), userMxId);
		auditRecord.setValue(context, AuditRecord.MemberNames.UserGUID.toString(), userGUID);
		auditRecord.setValue(context, AuditRecord.MemberNames.UserName.toString(), userName);
		Core.commit(context, auditRecord);
		
		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "LogAuditAction";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
