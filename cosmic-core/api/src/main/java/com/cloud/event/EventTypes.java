package com.cloud.event;

import com.cloud.config.Configuration;
import com.cloud.host.Host;
import com.cloud.legacymodel.dc.DataCenter;
import com.cloud.legacymodel.dc.Pod;
import com.cloud.legacymodel.dc.StorageNetworkIpRange;
import com.cloud.legacymodel.dc.Vlan;
import com.cloud.legacymodel.domain.Domain;
import com.cloud.legacymodel.network.FirewallRule;
import com.cloud.legacymodel.network.vpc.NetworkACL;
import com.cloud.legacymodel.network.vpc.NetworkACLItem;
import com.cloud.legacymodel.network.vpc.PrivateGateway;
import com.cloud.legacymodel.network.vpc.StaticRoute;
import com.cloud.legacymodel.network.vpc.Vpc;
import com.cloud.legacymodel.storage.StoragePool;
import com.cloud.legacymodel.user.Account;
import com.cloud.legacymodel.user.User;
import com.cloud.network.GuestVlan;
import com.cloud.network.IpAddress;
import com.cloud.network.Network;
import com.cloud.network.PhysicalNetwork;
import com.cloud.network.PhysicalNetworkServiceProvider;
import com.cloud.network.PhysicalNetworkTrafficType;
import com.cloud.network.RemoteAccessVpn;
import com.cloud.network.Site2SiteCustomerGateway;
import com.cloud.network.Site2SiteVpnConnection;
import com.cloud.network.Site2SiteVpnGateway;
import com.cloud.network.router.VirtualRouter;
import com.cloud.network.rules.HealthCheckPolicy;
import com.cloud.network.rules.LoadBalancer;
import com.cloud.network.rules.StaticNat;
import com.cloud.network.rules.StickinessPolicy;
import com.cloud.offering.DiskOffering;
import com.cloud.offering.NetworkOffering;
import com.cloud.offering.ServiceOffering;
import com.cloud.projects.Project;
import com.cloud.server.ResourceTag;
import com.cloud.storage.GuestOS;
import com.cloud.storage.GuestOSHypervisor;
import com.cloud.storage.Snapshot;
import com.cloud.storage.Volume;
import com.cloud.template.VirtualMachineTemplate;
import com.cloud.vm.Nic;
import com.cloud.vm.NicSecondaryIp;
import com.cloud.vm.VirtualMachine;

import java.util.HashMap;
import java.util.Map;

public class EventTypes {

    // VM Events
    public static final String EVENT_VM_CREATE = "VM.CREATE";
    public static final String EVENT_VM_DESTROY = "VM.DESTROY";
    public static final String EVENT_VM_START = "VM.START";
    public static final String EVENT_VM_STOP = "VM.STOP";
    public static final String EVENT_VM_REBOOT = "VM.REBOOT";
    public static final String EVENT_VM_UPDATE = "VM.UPDATE";
    public static final String EVENT_VM_UPGRADE = "VM.UPGRADE";
    public static final String EVENT_VM_DYNAMIC_SCALE = "VM.DYNAMIC.SCALE";
    public static final String EVENT_VM_RESETPASSWORD = "VM.RESETPASSWORD";
    public static final String EVENT_VM_RESETSSHKEY = "VM.RESETSSHKEY";
    public static final String EVENT_VM_MIGRATE = "VM.MIGRATE";
    public static final String EVENT_VM_MOVE = "VM.MOVE";
    public static final String EVENT_VM_RESTORE = "VM.RESTORE";
    public static final String EVENT_VM_EXPUNGE = "VM.EXPUNGE";
    // Domain Router
    public static final String EVENT_ROUTER_CREATE = "ROUTER.CREATE";
    public static final String EVENT_ROUTER_DESTROY = "ROUTER.DESTROY";
    public static final String EVENT_ROUTER_START = "ROUTER.START";
    public static final String EVENT_ROUTER_STOP = "ROUTER.STOP";
    public static final String EVENT_ROUTER_REBOOT = "ROUTER.REBOOT";
    public static final String EVENT_ROUTER_HA = "ROUTER.HA";
    public static final String EVENT_ROUTER_UPGRADE = "ROUTER.UPGRADE";
    // Console proxy
    public static final String EVENT_PROXY_CREATE = "PROXY.CREATE";
    public static final String EVENT_PROXY_DESTROY = "PROXY.DESTROY";
    public static final String EVENT_PROXY_START = "PROXY.START";
    public static final String EVENT_PROXY_STOP = "PROXY.STOP";
    public static final String EVENT_PROXY_REBOOT = "PROXY.REBOOT";
    public static final String EVENT_PROXY_HA = "PROXY.HA";
    // VNC Console Events
    public static final String EVENT_VNC_CONNECT = "VNC.CONNECT";
    public static final String EVENT_VNC_DISCONNECT = "VNC.DISCONNECT";
    // Network Events
    public static final String EVENT_NET_IP_ASSIGN = "NET.IPASSIGN";
    public static final String EVENT_NET_IP_RELEASE = "NET.IPRELEASE";
    public static final String EVENT_NET_IP_UPDATE = "NET.IPUPDATE";
    public static final String EVENT_NET_RULE_ADD = "NET.RULEADD";
    public static final String EVENT_NET_RULE_DELETE = "NET.RULEDELETE";
    public static final String EVENT_NET_RULE_MODIFY = "NET.RULEMODIFY";
    public static final String EVENT_NETWORK_CREATE = "NETWORK.CREATE";
    public static final String EVENT_NETWORK_DELETE = "NETWORK.DELETE";
    public static final String EVENT_NETWORK_UPDATE = "NETWORK.UPDATE";
    public static final String EVENT_FIREWALL_OPEN = "FIREWALL.OPEN";
    public static final String EVENT_FIREWALL_CLOSE = "FIREWALL.CLOSE";
    public static final String EVENT_FIREWALL_UPDATE = "FIREWALL.UPDATE";
    public static final String EVENT_FIREWALL_EGRESS_OPEN = "FIREWALL.EGRESS.OPEN";
    public static final String EVENT_FIREWALL_EGRESS_CLOSE = "FIREWALL.EGRESS.CLOSE";
    public static final String EVENT_FIREWALL_EGRESS_UPDATE = "FIREWALL.EGRESS.UPDATE";
    //NIC Events
    public static final String EVENT_NIC_CREATE = "NIC.CREATE";
    public static final String EVENT_NIC_DELETE = "NIC.DELETE";
    public static final String EVENT_NIC_UPDATE = "NIC.UPDATE";
    public static final String EVENT_NIC_DETAIL_ADD = "NIC.DETAIL.ADD";
    public static final String EVENT_NIC_DETAIL_UPDATE = "NIC.DETAIL.UPDATE";
    public static final String EVENT_NIC_DETAIL_REMOVE = "NIC.DETAIL.REMOVE";
    // Load Balancers
    public static final String EVENT_ASSIGN_TO_LOAD_BALANCER_RULE = "LB.ASSIGN.TO.RULE";
    public static final String EVENT_REMOVE_FROM_LOAD_BALANCER_RULE = "LB.REMOVE.FROM.RULE";
    public static final String EVENT_LOAD_BALANCER_CREATE = "LB.CREATE";
    public static final String EVENT_LOAD_BALANCER_DELETE = "LB.DELETE";
    public static final String EVENT_LB_STICKINESSPOLICY_CREATE = "LB.STICKINESSPOLICY.CREATE";
    public static final String EVENT_LB_STICKINESSPOLICY_UPDATE = "LB.STICKINESSPOLICY.UPDATE";
    public static final String EVENT_LB_STICKINESSPOLICY_DELETE = "LB.STICKINESSPOLICY.DELETE";
    public static final String EVENT_LB_HEALTHCHECKPOLICY_CREATE = "LB.HEALTHCHECKPOLICY.CREATE";
    public static final String EVENT_LB_HEALTHCHECKPOLICY_DELETE = "LB.HEALTHCHECKPOLICY.DELETE";
    public static final String EVENT_LB_HEALTHCHECKPOLICY_UPDATE = "LB.HEALTHCHECKPOLICY.UPDATE";
    public static final String EVENT_LOAD_BALANCER_UPDATE = "LB.UPDATE";
    public static final String EVENT_LB_CERT_UPLOAD = "LB.CERT.UPLOAD";
    public static final String EVENT_LB_CERT_DELETE = "LB.CERT.DELETE";
    public static final String EVENT_LB_CERT_ASSIGN = "LB.CERT.ASSIGN";
    public static final String EVENT_LB_CERT_REMOVE = "LB.CERT.REMOVE";
    // Global Load Balancer rules
    public static final String EVENT_ASSIGN_TO_GLOBAL_LOAD_BALANCER_RULE = "GLOBAL.LB.ASSIGN";
    public static final String EVENT_REMOVE_FROM_GLOBAL_LOAD_BALANCER_RULE = "GLOBAL.LB.REMOVE";
    public static final String EVENT_GLOBAL_LOAD_BALANCER_CREATE = "GLOBAL.LB.CREATE";
    public static final String EVENT_GLOBAL_LOAD_BALANCER_DELETE = "GLOBAL.LB.DELETE";
    public static final String EVENT_GLOBAL_LOAD_BALANCER_UPDATE = "GLOBAL.LB.UPDATE";
    // Account events
    public static final String EVENT_ACCOUNT_ENABLE = "ACCOUNT.ENABLE";
    public static final String EVENT_ACCOUNT_DISABLE = "ACCOUNT.DISABLE";
    public static final String EVENT_ACCOUNT_CREATE = "ACCOUNT.CREATE";
    public static final String EVENT_ACCOUNT_DELETE = "ACCOUNT.DELETE";
    public static final String EVENT_ACCOUNT_UPDATE = "ACCOUNT.UPDATE";
    public static final String EVENT_ACCOUNT_MARK_DEFAULT_ZONE = "ACCOUNT.MARK.DEFAULT.ZONE";
    // UserVO Events
    public static final String EVENT_USER_LOGIN = "USER.LOGIN";
    public static final String EVENT_USER_LOGOUT = "USER.LOGOUT";
    public static final String EVENT_USER_CREATE = "USER.CREATE";
    public static final String EVENT_USER_DELETE = "USER.DELETE";
    public static final String EVENT_USER_DISABLE = "USER.DISABLE";
    public static final String EVENT_USER_UPDATE = "USER.UPDATE";
    public static final String EVENT_USER_ENABLE = "USER.ENABLE";
    public static final String EVENT_USER_LOCK = "USER.LOCK";
    //registering SSH keypair events
    public static final String EVENT_REGISTER_SSH_KEYPAIR = "REGISTER.SSH.KEYPAIR";
    //register for user API and secret keys
    public static final String EVENT_REGISTER_FOR_SECRET_API_KEY = "REGISTER.USER.KEY";
    // Template Events
    public static final String EVENT_TEMPLATE_CREATE = "TEMPLATE.CREATE";
    public static final String EVENT_TEMPLATE_DELETE = "TEMPLATE.DELETE";
    public static final String EVENT_TEMPLATE_UPDATE = "TEMPLATE.UPDATE";
    public static final String EVENT_TEMPLATE_DOWNLOAD_START = "TEMPLATE.DOWNLOAD.START";
    public static final String EVENT_TEMPLATE_DOWNLOAD_SUCCESS = "TEMPLATE.DOWNLOAD.SUCCESS";
    public static final String EVENT_TEMPLATE_DOWNLOAD_FAILED = "TEMPLATE.DOWNLOAD.FAILED";
    public static final String EVENT_TEMPLATE_COPY = "TEMPLATE.COPY";
    public static final String EVENT_TEMPLATE_EXTRACT = "TEMPLATE.EXTRACT";
    public static final String EVENT_TEMPLATE_UPLOAD = "TEMPLATE.UPLOAD";
    public static final String EVENT_TEMPLATE_CLEANUP = "TEMPLATE.CLEANUP";
    // Volume Events
    public static final String EVENT_VOLUME_CREATE = "VOLUME.CREATE";
    public static final String EVENT_VOLUME_DELETE = "VOLUME.DELETE";
    public static final String EVENT_VOLUME_ATTACH = "VOLUME.ATTACH";
    public static final String EVENT_VOLUME_DETACH = "VOLUME.DETACH";
    public static final String EVENT_VOLUME_EXTRACT = "VOLUME.EXTRACT";
    public static final String EVENT_VOLUME_UPLOAD = "VOLUME.UPLOAD";
    public static final String EVENT_VOLUME_MIGRATE = "VOLUME.MIGRATE";
    public static final String EVENT_VOLUME_RESIZE = "VOLUME.RESIZE";
    public static final String EVENT_VOLUME_DETAIL_UPDATE = "VOLUME.DETAIL.UPDATE";
    public static final String EVENT_VOLUME_DETAIL_ADD = "VOLUME.DETAIL.ADD";
    public static final String EVENT_VOLUME_DETAIL_REMOVE = "VOLUME.DETAIL.REMOVE";
    public static final String EVENT_VOLUME_UPDATE = "VOLUME.UPDATE";
    // Domains
    public static final String EVENT_DOMAIN_CREATE = "DOMAIN.CREATE";
    public static final String EVENT_DOMAIN_DELETE = "DOMAIN.DELETE";
    public static final String EVENT_DOMAIN_UPDATE = "DOMAIN.UPDATE";
    // Snapshots
    public static final String EVENT_SNAPSHOT_CREATE = "SNAPSHOT.CREATE";
    public static final String EVENT_SNAPSHOT_DELETE = "SNAPSHOT.DELETE";
    public static final String EVENT_SNAPSHOT_REVERT = "SNAPSHOT.REVERT";
    public static final String EVENT_SNAPSHOT_POLICY_CREATE = "SNAPSHOTPOLICY.CREATE";
    public static final String EVENT_SNAPSHOT_POLICY_UPDATE = "SNAPSHOTPOLICY.UPDATE";
    public static final String EVENT_SNAPSHOT_POLICY_DELETE = "SNAPSHOTPOLICY.DELETE";
    // ISO
    public static final String EVENT_ISO_CREATE = "ISO.CREATE";
    public static final String EVENT_ISO_DELETE = "ISO.DELETE";
    public static final String EVENT_ISO_COPY = "ISO.COPY";
    public static final String EVENT_ISO_ATTACH = "ISO.ATTACH";
    public static final String EVENT_ISO_DETACH = "ISO.DETACH";
    public static final String EVENT_ISO_EXTRACT = "ISO.EXTRACT";
    public static final String EVENT_ISO_UPLOAD = "ISO.UPLOAD";
    // SSVM
    public static final String EVENT_SSVM_CREATE = "SSVM.CREATE";
    public static final String EVENT_SSVM_DESTROY = "SSVM.DESTROY";
    public static final String EVENT_SSVM_START = "SSVM.START";
    public static final String EVENT_SSVM_STOP = "SSVM.STOP";
    public static final String EVENT_SSVM_REBOOT = "SSVM.REBOOT";
    public static final String EVENT_SSVM_HA = "SSVM.HA";
    // Service Offerings
    public static final String EVENT_SERVICE_OFFERING_CREATE = "SERVICE.OFFERING.CREATE";
    public static final String EVENT_SERVICE_OFFERING_EDIT = "SERVICE.OFFERING.EDIT";
    public static final String EVENT_SERVICE_OFFERING_DELETE = "SERVICE.OFFERING.DELETE";
    // Disk Offerings
    public static final String EVENT_DISK_OFFERING_CREATE = "DISK.OFFERING.CREATE";
    public static final String EVENT_DISK_OFFERING_EDIT = "DISK.OFFERING.EDIT";
    public static final String EVENT_DISK_OFFERING_DELETE = "DISK.OFFERING.DELETE";
    // Network offerings
    public static final String EVENT_NETWORK_OFFERING_CREATE = "NETWORK.OFFERING.CREATE";
    public static final String EVENT_NETWORK_OFFERING_ASSIGN = "NETWORK.OFFERING.ASSIGN";
    public static final String EVENT_NETWORK_OFFERING_EDIT = "NETWORK.OFFERING.EDIT";
    public static final String EVENT_NETWORK_OFFERING_REMOVE = "NETWORK.OFFERING.REMOVE";
    public static final String EVENT_NETWORK_OFFERING_DELETE = "NETWORK.OFFERING.DELETE";
    // Pods
    public static final String EVENT_POD_CREATE = "POD.CREATE";
    public static final String EVENT_POD_EDIT = "POD.EDIT";
    public static final String EVENT_POD_DELETE = "POD.DELETE";
    // Zones
    public static final String EVENT_ZONE_CREATE = "ZONE.CREATE";
    public static final String EVENT_ZONE_EDIT = "ZONE.EDIT";
    public static final String EVENT_ZONE_DELETE = "ZONE.DELETE";
    // VLANs/IP ranges
    public static final String EVENT_VLAN_IP_RANGE_CREATE = "VLAN.IP.RANGE.CREATE";
    public static final String EVENT_VLAN_IP_RANGE_DELETE = "VLAN.IP.RANGE.DELETE";
    public static final String EVENT_VLAN_IP_RANGE_DEDICATE = "VLAN.IP.RANGE.DEDICATE";
    public static final String EVENT_VLAN_IP_RANGE_RELEASE = "VLAN.IP.RANGE.RELEASE";
    public static final String EVENT_STORAGE_IP_RANGE_CREATE = "STORAGE.IP.RANGE.CREATE";
    public static final String EVENT_STORAGE_IP_RANGE_DELETE = "STORAGE.IP.RANGE.DELETE";
    public static final String EVENT_STORAGE_IP_RANGE_UPDATE = "STORAGE.IP.RANGE.UPDATE";
    // Configuration Table
    public static final String EVENT_CONFIGURATION_VALUE_EDIT = "CONFIGURATION.VALUE.EDIT";
    // Host
    public static final String EVENT_HOST_RECONNECT = "HOST.RECONNECT";
    // Maintenance
    public static final String EVENT_MAINTENANCE_CANCEL = "MAINT.CANCEL";
    public static final String EVENT_MAINTENANCE_CANCEL_PRIMARY_STORAGE = "MAINT.CANCEL.PS";
    public static final String EVENT_MAINTENANCE_PREPARE = "MAINT.PREPARE";
    public static final String EVENT_MAINTENANCE_PREPARE_PRIMARY_STORAGE = "MAINT.PREPARE.PS";
    // Primary storage pool
    public static final String EVENT_ENABLE_PRIMARY_STORAGE = "ENABLE.PS";
    public static final String EVENT_DISABLE_PRIMARY_STORAGE = "DISABLE.PS";
    // VPN
    public static final String EVENT_REMOTE_ACCESS_VPN_CREATE = "VPN.REMOTE.ACCESS.CREATE";
    public static final String EVENT_REMOTE_ACCESS_VPN_DESTROY = "VPN.REMOTE.ACCESS.DESTROY";
    public static final String EVENT_REMOTE_ACCESS_VPN_UPDATE = "VPN.REMOTE.ACCESS.UPDATE";
    public static final String EVENT_VPN_USER_ADD = "VPN.USER.ADD";
    public static final String EVENT_VPN_USER_REMOVE = "VPN.USER.REMOVE";
    public static final String EVENT_S2S_VPN_GATEWAY_CREATE = "VPN.S2S.VPN.GATEWAY.CREATE";
    public static final String EVENT_S2S_VPN_GATEWAY_DELETE = "VPN.S2S.VPN.GATEWAY.DELETE";
    public static final String EVENT_S2S_VPN_GATEWAY_UPDATE = "VPN.S2S.VPN.GATEWAY.UPDATE";
    public static final String EVENT_S2S_VPN_CUSTOMER_GATEWAY_CREATE = "VPN.S2S.CUSTOMER.GATEWAY.CREATE";
    public static final String EVENT_S2S_VPN_CUSTOMER_GATEWAY_DELETE = "VPN.S2S.CUSTOMER.GATEWAY.DELETE";
    public static final String EVENT_S2S_VPN_CUSTOMER_GATEWAY_UPDATE = "VPN.S2S.CUSTOMER.GATEWAY.UPDATE";
    public static final String EVENT_S2S_VPN_CONNECTION_CREATE = "VPN.S2S.CONNECTION.CREATE";
    public static final String EVENT_S2S_VPN_CONNECTION_DELETE = "VPN.S2S.CONNECTION.DELETE";
    public static final String EVENT_S2S_VPN_CONNECTION_RESET = "VPN.S2S.CONNECTION.RESET";
    public static final String EVENT_S2S_VPN_CONNECTION_UPDATE = "VPN.S2S.CONNECTION.UPDATE";
    // Network
    public static final String EVENT_NETWORK_RESTART = "NETWORK.RESTART";
    // Custom certificates
    public static final String EVENT_UPLOAD_CUSTOM_CERTIFICATE = "UPLOAD.CUSTOM.CERTIFICATE";
    // OneToOnenat
    public static final String EVENT_ENABLE_STATIC_NAT = "STATICNAT.ENABLE";
    public static final String EVENT_DISABLE_STATIC_NAT = "STATICNAT.DISABLE";
    public static final String EVENT_ZONE_VLAN_ASSIGN = "ZONE.VLAN.ASSIGN";
    public static final String EVENT_ZONE_VLAN_RELEASE = "ZONE.VLAN.RELEASE";
    // Projects
    public static final String EVENT_PROJECT_CREATE = "PROJECT.CREATE";
    public static final String EVENT_PROJECT_UPDATE = "PROJECT.UPDATE";
    public static final String EVENT_PROJECT_DELETE = "PROJECT.DELETE";
    public static final String EVENT_PROJECT_ACTIVATE = "PROJECT.ACTIVATE";
    public static final String EVENT_PROJECT_SUSPEND = "PROJECT.SUSPEND";
    public static final String EVENT_PROJECT_ACCOUNT_ADD = "PROJECT.ACCOUNT.ADD";
    public static final String EVENT_PROJECT_INVITATION_UPDATE = "PROJECT.INVITATION.UPDATE";
    public static final String EVENT_PROJECT_INVITATION_REMOVE = "PROJECT.INVITATION.REMOVE";
    public static final String EVENT_PROJECT_ACCOUNT_REMOVE = "PROJECT.ACCOUNT.REMOVE";
    // Network as a Service
    public static final String EVENT_NETWORK_ELEMENT_CONFIGURE = "NETWORK.ELEMENT.CONFIGURE";
    // Physical Network Events
    public static final String EVENT_PHYSICAL_NETWORK_CREATE = "PHYSICAL.NETWORK.CREATE";
    public static final String EVENT_PHYSICAL_NETWORK_DELETE = "PHYSICAL.NETWORK.DELETE";
    public static final String EVENT_PHYSICAL_NETWORK_UPDATE = "PHYSICAL.NETWORK.UPDATE";
    // Physical Network Service Provider Events
    public static final String EVENT_SERVICE_PROVIDER_CREATE = "SERVICE.PROVIDER.CREATE";
    public static final String EVENT_SERVICE_PROVIDER_DELETE = "SERVICE.PROVIDER.DELETE";
    public static final String EVENT_SERVICE_PROVIDER_UPDATE = "SERVICE.PROVIDER.UPDATE";
    // Physical Network TrafficType Events
    public static final String EVENT_TRAFFIC_TYPE_CREATE = "TRAFFIC.TYPE.CREATE";
    public static final String EVENT_TRAFFIC_TYPE_DELETE = "TRAFFIC.TYPE.DELETE";
    public static final String EVENT_TRAFFIC_TYPE_UPDATE = "TRAFFIC.TYPE.UPDATE";
    // external network device events
    public static final String EVENT_EXTERNAL_LB_DEVICE_ADD = "PHYSICAL.LOADBALANCER.ADD";
    public static final String EVENT_EXTERNAL_LB_DEVICE_DELETE = "PHYSICAL.LOADBALANCER.DELETE";
    public static final String EVENT_EXTERNAL_LB_DEVICE_CONFIGURE = "PHYSICAL.LOADBALANCER.CONFIGURE";
    // external switch management device events (E.g.: Cisco Nexus 1000v Virtual Supervisor Module.
    public static final String EVENT_EXTERNAL_SWITCH_MGMT_DEVICE_ADD = "SWITCH.MGMT.ADD";
    public static final String EVENT_EXTERNAL_SWITCH_MGMT_DEVICE_DELETE = "SWITCH.MGMT.DELETE";
    public static final String EVENT_EXTERNAL_SWITCH_MGMT_DEVICE_CONFIGURE = "SWITCH.MGMT.CONFIGURE";
    public static final String EVENT_EXTERNAL_SWITCH_MGMT_DEVICE_ENABLE = "SWITCH.MGMT.ENABLE";
    public static final String EVENT_EXTERNAL_SWITCH_MGMT_DEVICE_DISABLE = "SWITCH.MGMT.DISABLE";
    public static final String EVENT_EXTERNAL_FIREWALL_DEVICE_ADD = "PHYSICAL.FIREWALL.ADD";
    public static final String EVENT_EXTERNAL_FIREWALL_DEVICE_DELETE = "PHYSICAL.FIREWALL.DELETE";
    public static final String EVENT_EXTERNAL_FIREWALL_DEVICE_CONFIGURE = "PHYSICAL.FIREWALL.CONFIGURE";
    // VPC
    public static final String EVENT_VPC_CREATE = "VPC.CREATE";
    public static final String EVENT_VPC_UPDATE = "VPC.UPDATE";
    public static final String EVENT_VPC_DELETE = "VPC.DELETE";
    public static final String EVENT_VPC_RESTART = "VPC.RESTART";
    // Network ACL
    public static final String EVENT_NETWORK_ACL_CREATE = "NETWORK.ACL.CREATE";
    public static final String EVENT_NETWORK_ACL_DELETE = "NETWORK.ACL.DELETE";
    public static final String EVENT_NETWORK_ACL_REPLACE = "NETWORK.ACL.REPLACE";
    public static final String EVENT_NETWORK_ACL_UPDATE = "NETWORK.ACL.UPDATE";
    public static final String EVENT_NETWORK_ACL_ITEM_CREATE = "NETWORK.ACL.ITEM.CREATE";
    public static final String EVENT_NETWORK_ACL_ITEM_UPDATE = "NETWORK.ACL.ITEM.UPDATE";
    public static final String EVENT_NETWORK_ACL_ITEM_DELETE = "NETWORK.ACL.ITEM.DELETE";
    // VPC offerings
    public static final String EVENT_VPC_OFFERING_CREATE = "VPC.OFFERING.CREATE";
    public static final String EVENT_VPC_OFFERING_UPDATE = "VPC.OFFERING.UPDATE";
    public static final String EVENT_VPC_OFFERING_DELETE = "VPC.OFFERING.DELETE";
    // Private gateway
    public static final String EVENT_PRIVATE_GATEWAY_CREATE = "PRIVATE.GATEWAY.CREATE";
    public static final String EVENT_PRIVATE_GATEWAY_DELETE = "PRIVATE.GATEWAY.DELETE";
    // Static routes
    public static final String EVENT_STATIC_ROUTE_CREATE = "STATIC.ROUTE.CREATE";
    public static final String EVENT_STATIC_ROUTE_DELETE = "STATIC.ROUTE.DELETE";
    // tag related events
    public static final String EVENT_TAGS_CREATE = "CREATE_TAGS";
    public static final String EVENT_TAGS_DELETE = "DELETE_TAGS";
    // meta data related events
    public static final String EVENT_RESOURCE_DETAILS_CREATE = "CREATE_RESOURCE_DETAILS";
    public static final String EVENT_RESOURCE_DETAILS_DELETE = "DELETE_RESOURCE_DETAILS";
    // vm snapshot events
    public static final String EVENT_VM_SNAPSHOT_CREATE = "VMSNAPSHOT.CREATE";
    public static final String EVENT_VM_SNAPSHOT_DELETE = "VMSNAPSHOT.DELETE";
    public static final String EVENT_VM_SNAPSHOT_REVERT = "VMSNAPSHOT.REVERTTO";
    // external network device events
    public static final String EVENT_EXTERNAL_NVP_CONTROLLER_ADD = "PHYSICAL.NVPCONTROLLER.ADD";
    public static final String EVENT_EXTERNAL_NVP_CONTROLLER_DELETE = "PHYSICAL.NVPCONTROLLER.DELETE";
    public static final String EVENT_EXTERNAL_NVP_CONTROLLER_CONFIGURE = "PHYSICAL.NVPCONTROLLER.CONFIGURE";

    public static final String EVENT_AFFINITY_GROUP_CREATE = "AG.CREATE";
    public static final String EVENT_AFFINITY_GROUP_DELETE = "AG.DELETE";
    public static final String EVENT_AFFINITY_GROUP_ASSIGN = "AG.ASSIGN";
    public static final String EVENT_AFFINITY_GROUP_REMOVE = "AG.REMOVE";
    public static final String EVENT_VM_AFFINITY_GROUP_UPDATE = "VM.AG.UPDATE";
    public static final String EVENT_HOST_RESERVATION_RELEASE = "HOST.RESERVATION.RELEASE";

    // Dedicated guest vlan range
    public static final String EVENT_GUEST_VLAN_RANGE_DEDICATE = "GUESTVLANRANGE.DEDICATE";
    public static final String EVENT_DEDICATED_GUEST_VLAN_RANGE_RELEASE = "GUESTVLANRANGE.RELEASE";
    // Dedicated Resources
    public static final String EVENT_DEDICATE_RESOURCE = "DEDICATE.RESOURCE";
    public static final String EVENT_DEDICATE_RESOURCE_RELEASE = "DEDICATE.RESOURCE.RELEASE";
    public static final String EVENT_CLEANUP_VM_RESERVATION = "VM.RESERVATION.CLEANUP";
    // Object store migration
    public static final String EVENT_MIGRATE_PREPARE_SECONDARY_STORAGE = "MIGRATE.PREPARE.SS";
    //Alert generation
    public static final String ALERT_GENERATE = "ALERT.GENERATE";
    //Guest OS related events
    public static final String EVENT_GUEST_OS_ADD = "GUEST.OS.ADD";
    public static final String EVENT_GUEST_OS_REMOVE = "GUEST.OS.REMOVE";
    public static final String EVENT_GUEST_OS_UPDATE = "GUEST.OS.UPDATE";
    public static final String EVENT_GUEST_OS_MAPPING_ADD = "GUEST.OS.MAPPING.ADD";
    public static final String EVENT_GUEST_OS_MAPPING_REMOVE = "GUEST.OS.MAPPING.REMOVE";
    public static final String EVENT_GUEST_OS_MAPPING_UPDATE = "GUEST.OS.MAPPING.UPDATE";
    public static final String EVENT_NIC_SECONDARY_IP_ASSIGN = "NIC.SECONDARY.IP.ASSIGN";
    public static final String EVENT_NIC_SECONDARY_IP_UNASSIGN = "NIC.SECONDARY.IP.UNASSIGN";
    public static final String EVENT_NIC_SECONDARY_IP_CONFIGURE = "NIC.SECONDARY.IP.CONFIGURE";
    public static final String EVENT_NETWORK_EXTERNAL_DHCP_VM_IPFETCH = "EXTERNAL.DHCP.VM.IP.FETCH";
    //Usage related events
    public static final String EVENT_USAGE_REMOVE_USAGE_RECORDS = "USAGE.REMOVE.USAGE.RECORDS";
    //map of Event and corresponding entity for which Event is applicable
    private static Map<String, Object> entityEventDetails = null;

    static {

        // TODO: need a way to force author adding event types to declare the entity details as well, with out braking

        entityEventDetails = new HashMap<>();

        entityEventDetails.put(EVENT_VM_CREATE, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_DESTROY, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_START, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_STOP, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_REBOOT, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_UPDATE, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_UPGRADE, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_DYNAMIC_SCALE, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_RESETPASSWORD, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_RESETSSHKEY, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_MIGRATE, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_MOVE, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_RESTORE, VirtualMachine.class);
        entityEventDetails.put(EVENT_VM_EXPUNGE, VirtualMachine.class);

        entityEventDetails.put(EVENT_ROUTER_CREATE, VirtualRouter.class);
        entityEventDetails.put(EVENT_ROUTER_DESTROY, VirtualRouter.class);
        entityEventDetails.put(EVENT_ROUTER_START, VirtualRouter.class);
        entityEventDetails.put(EVENT_ROUTER_STOP, VirtualRouter.class);
        entityEventDetails.put(EVENT_ROUTER_REBOOT, VirtualRouter.class);
        entityEventDetails.put(EVENT_ROUTER_HA, VirtualRouter.class);
        entityEventDetails.put(EVENT_ROUTER_UPGRADE, VirtualRouter.class);

        entityEventDetails.put(EVENT_PROXY_CREATE, VirtualMachine.class);
        entityEventDetails.put(EVENT_PROXY_DESTROY, VirtualMachine.class);
        entityEventDetails.put(EVENT_PROXY_START, VirtualMachine.class);
        entityEventDetails.put(EVENT_PROXY_STOP, VirtualMachine.class);
        entityEventDetails.put(EVENT_PROXY_REBOOT, VirtualMachine.class);
        entityEventDetails.put(EVENT_ROUTER_HA, VirtualMachine.class);
        entityEventDetails.put(EVENT_PROXY_HA, VirtualMachine.class);

        entityEventDetails.put(EVENT_VNC_CONNECT, "VNC");
        entityEventDetails.put(EVENT_VNC_DISCONNECT, "VNC");

        // Network Events
        entityEventDetails.put(EVENT_NETWORK_CREATE, Network.class);
        entityEventDetails.put(EVENT_NETWORK_DELETE, Network.class);
        entityEventDetails.put(EVENT_NETWORK_UPDATE, Network.class);
        entityEventDetails.put(EVENT_NETWORK_RESTART, Network.class);
        entityEventDetails.put(EVENT_NET_IP_ASSIGN, IpAddress.class);
        entityEventDetails.put(EVENT_NET_IP_RELEASE, IpAddress.class);
        entityEventDetails.put(EVENT_NET_RULE_ADD, FirewallRule.class);
        entityEventDetails.put(EVENT_NET_RULE_DELETE, FirewallRule.class);
        entityEventDetails.put(EVENT_NET_RULE_MODIFY, FirewallRule.class);
        entityEventDetails.put(EVENT_FIREWALL_OPEN, FirewallRule.class);
        entityEventDetails.put(EVENT_FIREWALL_CLOSE, FirewallRule.class);
        entityEventDetails.put(EVENT_FIREWALL_EGRESS_OPEN, FirewallRule.class);
        entityEventDetails.put(EVENT_FIREWALL_EGRESS_CLOSE, FirewallRule.class);
        entityEventDetails.put(EVENT_FIREWALL_EGRESS_UPDATE, FirewallRule.class);

        // Nic Events
        entityEventDetails.put(EVENT_NIC_CREATE, Nic.class);

        // Load Balancers
        entityEventDetails.put(EVENT_ASSIGN_TO_LOAD_BALANCER_RULE, FirewallRule.class);
        entityEventDetails.put(EVENT_REMOVE_FROM_LOAD_BALANCER_RULE, FirewallRule.class);
        entityEventDetails.put(EVENT_LOAD_BALANCER_CREATE, LoadBalancer.class);
        entityEventDetails.put(EVENT_LOAD_BALANCER_DELETE, FirewallRule.class);
        entityEventDetails.put(EVENT_LB_STICKINESSPOLICY_CREATE, StickinessPolicy.class);
        entityEventDetails.put(EVENT_LB_STICKINESSPOLICY_UPDATE, StickinessPolicy.class);
        entityEventDetails.put(EVENT_LB_STICKINESSPOLICY_DELETE, StickinessPolicy.class);
        entityEventDetails.put(EVENT_LB_HEALTHCHECKPOLICY_CREATE, HealthCheckPolicy.class);
        entityEventDetails.put(EVENT_LB_HEALTHCHECKPOLICY_UPDATE, HealthCheckPolicy.class);
        entityEventDetails.put(EVENT_LB_HEALTHCHECKPOLICY_DELETE, HealthCheckPolicy.class);
        entityEventDetails.put(EVENT_LOAD_BALANCER_UPDATE, LoadBalancer.class);
        entityEventDetails.put(EVENT_LB_CERT_UPLOAD, LoadBalancer.class);
        entityEventDetails.put(EVENT_LB_CERT_DELETE, LoadBalancer.class);
        entityEventDetails.put(EVENT_LB_CERT_ASSIGN, LoadBalancer.class);
        entityEventDetails.put(EVENT_LB_CERT_REMOVE, LoadBalancer.class);

        // Account events
        entityEventDetails.put(EVENT_ACCOUNT_ENABLE, Account.class);
        entityEventDetails.put(EVENT_ACCOUNT_DISABLE, Account.class);
        entityEventDetails.put(EVENT_ACCOUNT_CREATE, Account.class);
        entityEventDetails.put(EVENT_ACCOUNT_DELETE, Account.class);
        entityEventDetails.put(EVENT_ACCOUNT_UPDATE, Account.class);
        entityEventDetails.put(EVENT_ACCOUNT_MARK_DEFAULT_ZONE, Account.class);

        // UserVO Events
        entityEventDetails.put(EVENT_USER_LOGIN, User.class);
        entityEventDetails.put(EVENT_USER_LOGOUT, User.class);
        entityEventDetails.put(EVENT_USER_CREATE, User.class);
        entityEventDetails.put(EVENT_USER_DELETE, User.class);
        entityEventDetails.put(EVENT_USER_DISABLE, User.class);
        entityEventDetails.put(EVENT_USER_UPDATE, User.class);
        entityEventDetails.put(EVENT_USER_ENABLE, User.class);
        entityEventDetails.put(EVENT_USER_LOCK, User.class);

        // Template Events
        entityEventDetails.put(EVENT_TEMPLATE_CREATE, VirtualMachineTemplate.class);
        entityEventDetails.put(EVENT_TEMPLATE_DELETE, VirtualMachineTemplate.class);
        entityEventDetails.put(EVENT_TEMPLATE_UPDATE, VirtualMachineTemplate.class);
        entityEventDetails.put(EVENT_TEMPLATE_DOWNLOAD_START, VirtualMachineTemplate.class);
        entityEventDetails.put(EVENT_TEMPLATE_DOWNLOAD_SUCCESS, VirtualMachineTemplate.class);
        entityEventDetails.put(EVENT_TEMPLATE_DOWNLOAD_FAILED, VirtualMachineTemplate.class);
        entityEventDetails.put(EVENT_TEMPLATE_COPY, VirtualMachineTemplate.class);
        entityEventDetails.put(EVENT_TEMPLATE_EXTRACT, VirtualMachineTemplate.class);
        entityEventDetails.put(EVENT_TEMPLATE_UPLOAD, VirtualMachineTemplate.class);
        entityEventDetails.put(EVENT_TEMPLATE_CLEANUP, VirtualMachineTemplate.class);

        // Volume Events
        entityEventDetails.put(EVENT_VOLUME_CREATE, Volume.class);
        entityEventDetails.put(EVENT_VOLUME_DELETE, Volume.class);
        entityEventDetails.put(EVENT_VOLUME_ATTACH, Volume.class);
        entityEventDetails.put(EVENT_VOLUME_DETACH, Volume.class);
        entityEventDetails.put(EVENT_VOLUME_EXTRACT, Volume.class);
        entityEventDetails.put(EVENT_VOLUME_UPLOAD, Volume.class);
        entityEventDetails.put(EVENT_VOLUME_MIGRATE, Volume.class);
        entityEventDetails.put(EVENT_VOLUME_RESIZE, Volume.class);

        // Domains
        entityEventDetails.put(EVENT_DOMAIN_CREATE, Domain.class);
        entityEventDetails.put(EVENT_DOMAIN_DELETE, Domain.class);
        entityEventDetails.put(EVENT_DOMAIN_UPDATE, Domain.class);

        // Snapshots
        entityEventDetails.put(EVENT_SNAPSHOT_CREATE, Snapshot.class);
        entityEventDetails.put(EVENT_SNAPSHOT_DELETE, Snapshot.class);

        // ISO
        entityEventDetails.put(EVENT_ISO_CREATE, "Iso");
        entityEventDetails.put(EVENT_ISO_DELETE, "Iso");
        entityEventDetails.put(EVENT_ISO_COPY, "Iso");
        entityEventDetails.put(EVENT_ISO_ATTACH, "Iso");
        entityEventDetails.put(EVENT_ISO_DETACH, "Iso");
        entityEventDetails.put(EVENT_ISO_EXTRACT, "Iso");
        entityEventDetails.put(EVENT_ISO_UPLOAD, "Iso");

        // SSVM
        entityEventDetails.put(EVENT_SSVM_CREATE, VirtualMachine.class);
        entityEventDetails.put(EVENT_SSVM_DESTROY, VirtualMachine.class);
        entityEventDetails.put(EVENT_SSVM_START, VirtualMachine.class);
        entityEventDetails.put(EVENT_SSVM_STOP, VirtualMachine.class);
        entityEventDetails.put(EVENT_SSVM_REBOOT, VirtualMachine.class);
        entityEventDetails.put(EVENT_SSVM_HA, VirtualMachine.class);

        // Service Offerings
        entityEventDetails.put(EVENT_SERVICE_OFFERING_CREATE, ServiceOffering.class);
        entityEventDetails.put(EVENT_SERVICE_OFFERING_EDIT, ServiceOffering.class);
        entityEventDetails.put(EVENT_SERVICE_OFFERING_DELETE, ServiceOffering.class);

        // Disk Offerings
        entityEventDetails.put(EVENT_DISK_OFFERING_CREATE, DiskOffering.class);
        entityEventDetails.put(EVENT_DISK_OFFERING_EDIT, DiskOffering.class);
        entityEventDetails.put(EVENT_DISK_OFFERING_DELETE, DiskOffering.class);

        // Network offerings
        entityEventDetails.put(EVENT_NETWORK_OFFERING_CREATE, NetworkOffering.class);
        entityEventDetails.put(EVENT_NETWORK_OFFERING_ASSIGN, NetworkOffering.class);
        entityEventDetails.put(EVENT_NETWORK_OFFERING_EDIT, NetworkOffering.class);
        entityEventDetails.put(EVENT_NETWORK_OFFERING_REMOVE, NetworkOffering.class);
        entityEventDetails.put(EVENT_NETWORK_OFFERING_DELETE, NetworkOffering.class);

        // Pods
        entityEventDetails.put(EVENT_POD_CREATE, Pod.class);
        entityEventDetails.put(EVENT_POD_EDIT, Pod.class);
        entityEventDetails.put(EVENT_POD_DELETE, Pod.class);

        // Zones
        entityEventDetails.put(EVENT_ZONE_CREATE, DataCenter.class);
        entityEventDetails.put(EVENT_ZONE_EDIT, DataCenter.class);
        entityEventDetails.put(EVENT_ZONE_DELETE, DataCenter.class);

        // VLANs/IP ranges
        entityEventDetails.put(EVENT_VLAN_IP_RANGE_CREATE, Vlan.class);
        entityEventDetails.put(EVENT_VLAN_IP_RANGE_DELETE, Vlan.class);
        entityEventDetails.put(EVENT_VLAN_IP_RANGE_DEDICATE, Vlan.class);
        entityEventDetails.put(EVENT_VLAN_IP_RANGE_RELEASE, Vlan.class);

        entityEventDetails.put(EVENT_STORAGE_IP_RANGE_CREATE, StorageNetworkIpRange.class);
        entityEventDetails.put(EVENT_STORAGE_IP_RANGE_DELETE, StorageNetworkIpRange.class);
        entityEventDetails.put(EVENT_STORAGE_IP_RANGE_UPDATE, StorageNetworkIpRange.class);

        // Configuration Table
        entityEventDetails.put(EVENT_CONFIGURATION_VALUE_EDIT, Configuration.class);

        // Host
        entityEventDetails.put(EVENT_HOST_RECONNECT, Host.class);

        // Maintenance
        entityEventDetails.put(EVENT_MAINTENANCE_CANCEL, Host.class);
        entityEventDetails.put(EVENT_MAINTENANCE_CANCEL_PRIMARY_STORAGE, Host.class);
        entityEventDetails.put(EVENT_MAINTENANCE_PREPARE, Host.class);
        entityEventDetails.put(EVENT_MAINTENANCE_PREPARE_PRIMARY_STORAGE, Host.class);

        // Primary storage pool
        entityEventDetails.put(EVENT_ENABLE_PRIMARY_STORAGE, StoragePool.class);
        entityEventDetails.put(EVENT_DISABLE_PRIMARY_STORAGE, StoragePool.class);

        // VPN
        entityEventDetails.put(EVENT_REMOTE_ACCESS_VPN_CREATE, RemoteAccessVpn.class);
        entityEventDetails.put(EVENT_REMOTE_ACCESS_VPN_DESTROY, RemoteAccessVpn.class);
        entityEventDetails.put(EVENT_VPN_USER_ADD, RemoteAccessVpn.class);
        entityEventDetails.put(EVENT_VPN_USER_REMOVE, RemoteAccessVpn.class);
        entityEventDetails.put(EVENT_S2S_VPN_GATEWAY_CREATE, Site2SiteVpnGateway.class);
        entityEventDetails.put(EVENT_S2S_VPN_GATEWAY_DELETE, Site2SiteVpnGateway.class);
        entityEventDetails.put(EVENT_S2S_VPN_CUSTOMER_GATEWAY_CREATE, Site2SiteCustomerGateway.class);
        entityEventDetails.put(EVENT_S2S_VPN_CUSTOMER_GATEWAY_DELETE, Site2SiteCustomerGateway.class);
        entityEventDetails.put(EVENT_S2S_VPN_CUSTOMER_GATEWAY_UPDATE, Site2SiteCustomerGateway.class);
        entityEventDetails.put(EVENT_S2S_VPN_CONNECTION_CREATE, Site2SiteVpnConnection.class);
        entityEventDetails.put(EVENT_S2S_VPN_CONNECTION_DELETE, Site2SiteVpnConnection.class);
        entityEventDetails.put(EVENT_S2S_VPN_CONNECTION_RESET, Site2SiteVpnConnection.class);

        // Custom certificates
        entityEventDetails.put(EVENT_UPLOAD_CUSTOM_CERTIFICATE, "Certificate");

        // OneToOnenat
        entityEventDetails.put(EVENT_ENABLE_STATIC_NAT, StaticNat.class);
        entityEventDetails.put(EVENT_DISABLE_STATIC_NAT, StaticNat.class);

        entityEventDetails.put(EVENT_ZONE_VLAN_ASSIGN, Vlan.class);
        entityEventDetails.put(EVENT_ZONE_VLAN_RELEASE, Vlan.class);

        // Projects
        entityEventDetails.put(EVENT_PROJECT_CREATE, Project.class);
        entityEventDetails.put(EVENT_PROJECT_UPDATE, Project.class);
        entityEventDetails.put(EVENT_PROJECT_DELETE, Project.class);
        entityEventDetails.put(EVENT_PROJECT_ACTIVATE, Project.class);
        entityEventDetails.put(EVENT_PROJECT_SUSPEND, Project.class);
        entityEventDetails.put(EVENT_PROJECT_ACCOUNT_ADD, Project.class);
        entityEventDetails.put(EVENT_PROJECT_INVITATION_UPDATE, Project.class);
        entityEventDetails.put(EVENT_PROJECT_INVITATION_REMOVE, Project.class);
        entityEventDetails.put(EVENT_PROJECT_ACCOUNT_REMOVE, Project.class);

        // Network as a Service
        entityEventDetails.put(EVENT_NETWORK_ELEMENT_CONFIGURE, Network.class);

        // Physical Network Events
        entityEventDetails.put(EVENT_PHYSICAL_NETWORK_CREATE, PhysicalNetwork.class);
        entityEventDetails.put(EVENT_PHYSICAL_NETWORK_DELETE, PhysicalNetwork.class);
        entityEventDetails.put(EVENT_PHYSICAL_NETWORK_UPDATE, PhysicalNetwork.class);

        // Physical Network Service Provider Events
        entityEventDetails.put(EVENT_SERVICE_PROVIDER_CREATE, PhysicalNetworkServiceProvider.class);
        entityEventDetails.put(EVENT_SERVICE_PROVIDER_DELETE, PhysicalNetworkServiceProvider.class);
        entityEventDetails.put(EVENT_SERVICE_PROVIDER_UPDATE, PhysicalNetworkServiceProvider.class);

        // Physical Network TrafficType Events
        entityEventDetails.put(EVENT_TRAFFIC_TYPE_CREATE, PhysicalNetworkTrafficType.class);
        entityEventDetails.put(EVENT_TRAFFIC_TYPE_DELETE, PhysicalNetworkTrafficType.class);
        entityEventDetails.put(EVENT_TRAFFIC_TYPE_UPDATE, PhysicalNetworkTrafficType.class);

        // external network device events
        entityEventDetails.put(EVENT_EXTERNAL_LB_DEVICE_ADD, PhysicalNetwork.class);
        entityEventDetails.put(EVENT_EXTERNAL_LB_DEVICE_DELETE, PhysicalNetwork.class);
        entityEventDetails.put(EVENT_EXTERNAL_LB_DEVICE_CONFIGURE, PhysicalNetwork.class);

        // external switch management device events (E.g.: Cisco Nexus 1000v Virtual Supervisor Module.
        entityEventDetails.put(EVENT_EXTERNAL_SWITCH_MGMT_DEVICE_ADD, "Nexus1000v");
        entityEventDetails.put(EVENT_EXTERNAL_SWITCH_MGMT_DEVICE_DELETE, "Nexus1000v");
        entityEventDetails.put(EVENT_EXTERNAL_SWITCH_MGMT_DEVICE_CONFIGURE, "Nexus1000v");
        entityEventDetails.put(EVENT_EXTERNAL_SWITCH_MGMT_DEVICE_ENABLE, "Nexus1000v");
        entityEventDetails.put(EVENT_EXTERNAL_SWITCH_MGMT_DEVICE_DISABLE, "Nexus1000v");

        entityEventDetails.put(EVENT_EXTERNAL_FIREWALL_DEVICE_ADD, PhysicalNetwork.class);
        entityEventDetails.put(EVENT_EXTERNAL_FIREWALL_DEVICE_DELETE, PhysicalNetwork.class);
        entityEventDetails.put(EVENT_EXTERNAL_FIREWALL_DEVICE_CONFIGURE, PhysicalNetwork.class);

        // Network ACL
        entityEventDetails.put(EVENT_NETWORK_ACL_CREATE, NetworkACL.class);
        entityEventDetails.put(EVENT_NETWORK_ACL_DELETE, NetworkACL.class);
        entityEventDetails.put(EVENT_NETWORK_ACL_REPLACE, NetworkACL.class);
        entityEventDetails.put(EVENT_NETWORK_ACL_UPDATE, NetworkACL.class);
        entityEventDetails.put(EVENT_NETWORK_ACL_ITEM_CREATE, NetworkACLItem.class);
        entityEventDetails.put(EVENT_NETWORK_ACL_ITEM_UPDATE, NetworkACLItem.class);
        entityEventDetails.put(EVENT_NETWORK_ACL_ITEM_DELETE, NetworkACLItem.class);

        // VPC
        entityEventDetails.put(EVENT_VPC_CREATE, Vpc.class);
        entityEventDetails.put(EVENT_VPC_UPDATE, Vpc.class);
        entityEventDetails.put(EVENT_VPC_DELETE, Vpc.class);
        entityEventDetails.put(EVENT_VPC_RESTART, Vpc.class);

        // VPC offerings
        entityEventDetails.put(EVENT_VPC_OFFERING_CREATE, Vpc.class);
        entityEventDetails.put(EVENT_VPC_OFFERING_UPDATE, Vpc.class);
        entityEventDetails.put(EVENT_VPC_OFFERING_DELETE, Vpc.class);

        // Private gateway
        entityEventDetails.put(EVENT_PRIVATE_GATEWAY_CREATE, PrivateGateway.class);
        entityEventDetails.put(EVENT_PRIVATE_GATEWAY_DELETE, PrivateGateway.class);

        // Static routes
        entityEventDetails.put(EVENT_STATIC_ROUTE_CREATE, StaticRoute.class);
        entityEventDetails.put(EVENT_STATIC_ROUTE_DELETE, StaticRoute.class);

        // tag related events
        entityEventDetails.put(EVENT_TAGS_CREATE, ResourceTag.class);
        entityEventDetails.put(EVENT_TAGS_DELETE, ResourceTag.class);

        // external network device events
        entityEventDetails.put(EVENT_EXTERNAL_NVP_CONTROLLER_ADD, "NvpController");
        entityEventDetails.put(EVENT_EXTERNAL_NVP_CONTROLLER_DELETE, "NvpController");
        entityEventDetails.put(EVENT_EXTERNAL_NVP_CONTROLLER_CONFIGURE, "NvpController");

        entityEventDetails.put(EVENT_GUEST_VLAN_RANGE_DEDICATE, GuestVlan.class);
        entityEventDetails.put(EVENT_DEDICATED_GUEST_VLAN_RANGE_RELEASE, GuestVlan.class);

        //Guest OS
        entityEventDetails.put(EVENT_GUEST_OS_ADD, GuestOS.class);
        entityEventDetails.put(EVENT_GUEST_OS_REMOVE, GuestOS.class);
        entityEventDetails.put(EVENT_GUEST_OS_UPDATE, GuestOS.class);
        entityEventDetails.put(EVENT_GUEST_OS_MAPPING_ADD, GuestOSHypervisor.class);
        entityEventDetails.put(EVENT_GUEST_OS_MAPPING_REMOVE, GuestOSHypervisor.class);
        entityEventDetails.put(EVENT_GUEST_OS_MAPPING_UPDATE, GuestOSHypervisor.class);
        entityEventDetails.put(EVENT_NIC_SECONDARY_IP_ASSIGN, NicSecondaryIp.class);
        entityEventDetails.put(EVENT_NIC_SECONDARY_IP_UNASSIGN, NicSecondaryIp.class);
        entityEventDetails.put(EVENT_NIC_SECONDARY_IP_CONFIGURE, NicSecondaryIp.class);
    }

    public static String getEntityForEvent(final String eventName) {
        final Object entityClass = entityEventDetails.get(eventName);
        if (entityClass == null) {
            return null;
        } else if (entityClass instanceof String) {
            return (String) entityClass;
        } else if (entityClass instanceof Class) {
            final String entityClassName = ((Class) entityClass).getName();
            final int index = entityClassName.lastIndexOf(".");
            String entityName = entityClassName;
            if (index != -1) {
                entityName = entityClassName.substring(index + 1);
            }
            return entityName;
        }

        return null;
    }

    public static Class getEntityClassForEvent(final String eventName) {
        final Object clz = entityEventDetails.get(eventName);

        if (clz instanceof Class) {
            return (Class) entityEventDetails.get(eventName);
        }

        return null;
    }
}
