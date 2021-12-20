export class UsersByRole{
  id: string;
  createdTimestamp: number;
  username: string;
  enabled: boolean;
  totp: boolean;
  emailVerified: boolean;
  disableableCredentialTypes: [];
  requiredActions: [];
  notBefore: number;
}
