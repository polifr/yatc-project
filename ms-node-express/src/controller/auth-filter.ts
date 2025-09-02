import { Request, Response, NextFunction } from "express";
import jwt, { JwtHeader, JwtPayload } from "jsonwebtoken";
import { JwksClient, SigningKey } from "jwks-rsa";

export interface AuthRequest extends Request {
  user?: string | JwtPayload;
}

const client = new JwksClient({
  jwksUri: "http://yatc-keycloak/realms/yatc-realm/protocol/openid-connect/certs"
});

function getKey(header: JwtHeader, callback: (err: Error | null, key?: string) => void) {
  client.getSigningKey(header.kid as string, (err: Error | null, key?: SigningKey) => {
    if (err) {
      return callback(err);
    }
    const signingKey = key?.getPublicKey();
    callback(null, signingKey);
  });
}

export function validateToken(req: AuthRequest, res: Response, next: NextFunction) {
  const authHeader = req.headers["authorization"];
  if (!authHeader) {
    return res.status(401).json({ error: "Missing Authorization header" });
  }

  const token = authHeader.split(" ")[1];
  if (!token) {
    return res.status(401).json({ error: "Invalid Authorization header" });
  }

  jwt.verify(token, getKey, { algorithms: ["RS256"] }, (err, decoded) => {
    if (err) {
      return res.status(401).json({ error: "Invalid or expired token" });
    }
    req.user = decoded;
    next();
  });
}
